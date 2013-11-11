package com.github.groovy.astextras.local.strict

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

/**
 * This transformation will throw an exception every time it founds a method
 * with an access modifiers. Because Groovy doesn't care about the access modifiers
 * we should be using them either.
 *
 * @author marioggar
 */
@GroovyASTTransformation(phase=CompilePhase.INSTRUCTION_SELECTION)
class BlameAccessModifiersAst implements ASTTransformation {

    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

        if (!astNodes) return // Only if there is any annotation
        if (!astNodes[0] || !astNodes[1]) return //  astNodes[1] is the declaring class
        if (! (astNodes[0] instanceof AnnotationNode)) return
        if (astNodes[0].classNode?.name != BlameAccessModifiers.class.name) return


    }

}
