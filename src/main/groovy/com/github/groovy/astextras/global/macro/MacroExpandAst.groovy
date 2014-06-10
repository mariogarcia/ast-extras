package com.github.groovy.astextras.global.macro

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.codehaus.groovy.transform.AbstractASTTransformation

@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
class MacroExpandAst extends AbstractASTTransformation {

    void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        sourceUnit.AST.methods.each { method ->
            method.visit(new MacroExpandVisitor(sourceUnit))
        }
    }

}
