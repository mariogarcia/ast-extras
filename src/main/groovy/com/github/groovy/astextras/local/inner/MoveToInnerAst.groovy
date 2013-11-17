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

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
class MoveToInnerAst implements ASTTransformation {

    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

        if (!astNodes) return
		if (!astNodes[0] || !astNodes[1]) return
		if (!(astNodes[0] instanceof AnnotationNode)) return
		if (astNodes[0].classNode?.name != MoveToInner.class.name) return
		if (!(astNodes[1] instanceof MethodNode)) return

        MethodNode methodNode = astNodes[1]
        ClassNode declaringClass = methodNode.declaringClass
        String innerClassName = astNodes[0].members?.getAt('value')?.text
        String outerClassName = astNodes[1].declaringClass
        String innerClassFullName = outerClassName + '$' + innerClassName

        def innerClassNode = new AstBuilder().buildFromSpec {
            innerClass innerClassFullName, ClassNode.ACC_PUBLIC, {
                classNode outerClassName, ClassNode.ACC_PUBLIC, {
                    classNode Object
                    interfaces {
                        classNode GroovyObject
                    }
                    mixins { }
                }
                classNode Object
                interfaces {
                   classNode GroovyObject
                }
                mixins { }
            }
        }.first()

        def compilerConfiguration = declaringClass.compileUnit.config
        def compilationUnit = new CompilationUnit(compilerConfiguration)

        compilationUnit.addClassNode(innerClassNode)
        compilationUnit.compile()

        declaringClass.compileUnit.addClass(innerClassNode)

    }

}
