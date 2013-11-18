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

@GroovyASTTransformation(phase=CompilePhase.INSTRUCTION_SELECTION)
class MoveToInnerAst implements ASTTransformation {

    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

        if (!astNodes) return
		if (!astNodes[0] || !astNodes[1]) return
		if (!(astNodes[0] instanceof AnnotationNode)) return
		if (astNodes[0].classNode?.name != MoveToInner.class.name) return
		if (!(astNodes[1] instanceof MethodNode)) return

        def methodNode = astNodes[1]
        def declaringClass = methodNode.declaringClass
        def innerClassName = astNodes[0].members?.getAt('value')?.text
        def outerClassName = astNodes[1].declaringClass.name
        def innerClassFullName = outerClassName + '$' + innerClassName

        def innerClassNode = new AstBuilder().buildFromSpec {
            innerClass innerClassFullName, ClassNode.ACC_PUBLIC, {
                classNode outerClassName, ClassNode.ACC_PUBLIC, {
                    classNode Object
                    interfaces { classNode GroovyObject }
                    mixins { }
                }
                classNode Object
                interfaces { classNode GroovyObject }
                mixins { }
            }
        }.first()

        innerClassNode.addMethod(cloneNode(methodNode))

        def myClassNode = new AstBuilder().buildFromSpec {
            classNode(outerClassName, ClassNode.ACC_PUBLIC){
                classNode Object
                interfaces { classNode GroovyObject }
                mixins { }
            }
        }.first()

        def compilerConfiguration = sourceUnit.getAST().getUnit().config
        def compilationUnit = new CompilationUnit(compilerConfiguration)

        compilationUnit.addClassNode(myClassNode)
        compilationUnit.addClassNode(innerClassNode)
        compilationUnit.compile()

    }

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
