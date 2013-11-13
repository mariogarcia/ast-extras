package com.github.groovy.astextras.local.strict

import static java.lang.reflect.Modifier.PRIVATE

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
class BlamePrivateModifiersAst implements ASTTransformation {

    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

        if (!astNodes) return

        def (annotation, annotatedType) = astNodes[0..1]
        def nodesAreValid = checkNodes(annotation, annotatedType)

        if (!nodesAreValid) return

        annotatedType.allDeclaredMethods.each { methodNode ->
            methodNode.with {
                if (modifiers == PRIVATE) {
                    sourceUnit.addError(
                        new SyntaxException(
                            "Why are you using 'private' in $name?",
                            lineNumber,
                            columnNumber
                        )
                    )
                }
            }
        }
    }

    boolean checkNodes(AnnotationNode annotation, ClassNode annotatedType) {

        annotation &&
        annotatedType &&
        BlamePrivateModifiers.isInstance(annotation.classNode.declaredClass) &&
        ClassNode.isInstance(annotatedType)

    }

}
