package com.github.groovy.astextras.local.inner

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.AnnotationNode

import org.codehaus.groovy.ast.builder.AstBuilder

import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.CompilationUnit
import org.codehaus.groovy.control.CompilerConfiguration

import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

/**
 * This transformation takes the method annotated with @MoveToInner and effectively
 * moves it to an inner class which has the name of the value given as value to the
 * former annotation.
 *
 * This way a code like the following:
 * <pre>
 *   package com.github.groovy.astextras.local.inner
 *
 *   class Something {
 *     @MoveToInner("Foo")
 *     def myMethod() {
 *       return "Hello John"
 *     }
 *   }
 * </pre>
 *
 * Will become...
 *
 * <pre>
 *   package com.github.groovy.astextras.local.inner
 *
 *   class Something {
 *
 *      class Foo {
 *       def myMethod() {
 *          return "Hello John"
 *       }
 *      }
 *
 *   }
 * </pre>
 *
 *
 */
@GroovyASTTransformation(phase = CompilePhase.INSTRUCTION_SELECTION)
class MoveToInnerAst implements ASTTransformation {

    static final VALUE = 'value'
    static final DOLLAR = '$'
    static final PUBLIC = ClassNode.ACC_PUBLIC

    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

        if (!checkNodes(astNodes)) return

        def annotationNode = astNodes[0]
        def methodNode = astNodes[1]
        def declaringClass = methodNode.declaringClass

        def innerClassName = annotationNode.members.getAt(VALUE)?.text
        def outerClassName = declaringClass.name

        def outerClassNode = createOuterClass(outerClassName)
        def innerClassNode = createInnerClass(outerClassName, innerClassName)

        innerClassNode.addMethod(cloneNode(methodNode))

        def compilerConfiguration = sourceUnit.getAST().getUnit().config
        def compilationUnit =
            new CompilationUnit(compilerConfiguration).with {
                addClassNode(outerClassNode)
                addClassNode(innerClassNode)
                compile()
            }

    }

    /**
     * This method checks that the nodes passed as parameters are the ones we
     * want to visit
     *
     * @param astNodes The nodes we may want to process
     * @return true if the nodes are the ones we were looking for, false otherwise
     */
    def checkNodes(ASTNode[] astNodes) {

        astNodes &&
        astNodes[0] &&
        astNodes[1] &&
        astNodes[0] instanceof AnnotationNode &&
        astNodes[0].classNode?.name == MoveToInner.class.name &&
        astNodes[1] instanceof MethodNode

    }

    /**
     * This method creates an inner class
     *
     * @param qualifiedOuterClassName qualified outer class name (with the name of the package)
     * @param simpleInnerClassName name of the inner class (without the name of the package)
     * @return an instance of an InnerClassNode
     */
    def createInnerClass(String qualifiedOuterClassName, String simpleInnerClassName) {

        def innerClassFullName = qualifiedOuterClassName + DOLLAR + simpleInnerClassName

        new AstBuilder().buildFromSpec {
            innerClass(innerClassFullName, PUBLIC) {
                classNode(qualifiedOuterClassName, PUBLIC) {
                    classNode Object
                    interfaces { classNode GroovyObject }
                    mixins { }
                }
                classNode Object
                interfaces { classNode GroovyObject }
                mixins { }
            }
        }.first()

    }

    /**
     * This method creates an empty class node with the qualified name passed as parameter
     *
     * @param qualifiedClassNodeName The qualified name of the ClassNode we want to create
     * @return a new ClassNode instance
     */
    def createOuterClass(String qualifiedClassNodeName) {

        new AstBuilder().buildFromSpec {
            classNode(qualifiedClassNodeName, PUBLIC) {
                classNode Object
                interfaces { classNode GroovyObject }
                mixins { }
            }
        }.first()

    }

    /**
     * This method clones the method node passed as parameter
     *
     * @param methodNode the MethodNode instance we want to clone from
     * @return a cloned instance of the node passed as parameter
     */
    def cloneNode(MethodNode methodNode) {

        new MethodNode(
            methodNode.name,
            methodNode.modifiers,
            methodNode.returnType,
            methodNode.parameters,
            methodNode.exceptions,
            methodNode.code
        )

    }

}
