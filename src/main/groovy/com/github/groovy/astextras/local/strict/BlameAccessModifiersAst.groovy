package com.github.groovy.astextras.local.strict

import java.lang.reflect.Modifier

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.AnnotationNode

import org.codehaus.groovy.syntax.SyntaxException

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
        if (! (astNodes[1] instanceof ClassNode)) return
        if (astNodes[0].classNode?.name != BlameAccessModifiers.class.name) return

     /* Taking the type reference */
        ClassNode typeToInspect = astNodes[1]

     /* Looping through all the type methods */
        typeToInspect.allDeclaredMethods.each { methodNode ->

            def methodName = methodNode.name
            def isItPrivate = methodNode.modifiers == Modifier.PRIVATE

            if (isItPrivate) {
                sourceUnit.addError(
                    new SyntaxException(
                        "Why are you using an access modifier ?",0,0
                    )
                )
            }

        }

    }

}
