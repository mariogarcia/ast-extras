package com.github.groovy.astextras.local.inner

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.AnnotationNode

import org.codehaus.groovy.ast.builder.AstBuilder

import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase

import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation(phase=CompilePhase.INSTRUCTION_SELECTION)
class MoveToInnerAst implements ASTTransformation {

    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

        if (!astNodes) return
		if (!astNodes[0] || !astNodes[1]) return
		if (!(astNodes[0] instanceof AnnotationNode)) return
		if (astNodes[0].classNode?.name != MoveToInnerAst.class.name) return
		if (!(astNodes[1] instanceof MethodNode)) return

        MethodNode methodNode = astNodes[1]
        String innerClassName = astNodes[0].members?.getAt('value')?.text
        ClassNode classNode = new AstBuilder().buildFromSpec {
            innerClass 'Foo', ClassNode.ACC_PUBLIC, { }
        }.find { it }


    }

}
