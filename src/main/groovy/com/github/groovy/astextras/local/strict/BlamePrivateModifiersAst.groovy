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
class BlamePrivateModifiersAst implements ASTTransformation {

    def checkNodes(annotationElement, annotatedElement) {

        return
            annotationElement &&
            annotatedElement &&
            AnnotationNode.isInstance(annotationElement) &&
            ClassNode.isInstance(annotatedElement) &&
            BlamePrivateModifiers.isInstance(annotationElement.classNode)

    }

    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

        def annotationElement = astNodes[0]
        def annotatedElement = astNodes[1]
        def isItValid = validateNodes(annotationElement, annotatedElement)

        if (!isItValid) return

        annotatedElement.allDeclaredMethods.each { methodNode ->

            def methodName = methodNode.name
            def isItPrivate = methodNode.modifiers == Modifier.PRIVATE

            if (isItPrivate) {

                def line = methodNode.lineNumber
                def column = methodNode.columnNumber

                sourceUnit.addError(
                    new SyntaxException(
                        "Why are you using 'private' in $methodName?",
                        line,
                        column
                    )
                )
            }

        }

    }

}
