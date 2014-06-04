package com.github.groovy.astextras.local.methods

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.GroovyCodeVisitor
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.Variable
import org.codehaus.groovy.ast.VariableScope
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.ExpressionTransformer
import org.codehaus.groovy.ast.expr.MapEntryExpression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.DeclarationExpression
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.classgen.VariableScopeVisitor
import org.codehaus.groovy.classgen.asm.BinaryExpressionHelper
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.syntax.Types
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.codehaus.groovy.transform.AbstractASTTransformation

@GroovyASTTransformation(phase=CompilePhase.INSTRUCTION_SELECTION)
class ClosureScopeAst extends AbstractASTTransformation {

    void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        if (!nodes) return
        if (!(nodes[0] instanceof AnnotationNode)) return
        if (!(nodes[1] instanceof MethodNode)) return

        MethodNode methodNode = nodes[1]
        BlockStatement methodCode = methodNode.code

        // Getting to the check(...) call
        ExpressionStatement interestingStatement =
            methodCode.statements.findAll { stmt ->
                (stmt instanceof ExpressionStatement) &&
                (stmt.expression instanceof DeclarationExpression)
            }.last() // adhoc

        // Decomposing 'def result = check(...){}'

        DeclarationExpression result    = interestingStatement.expression
        MethodCallExpression check      = result.rightExpression

        ArgumentListExpression mapAndClosureArguments    = check.arguments
        MapExpression mapExpression         = mapAndClosureArguments.expressions.first()
        ClosureExpression closureExpression = mapAndClosureArguments.expressions.last()

        VariableScope closureVariableScope = closureExpression.variableScope
        VariableScope newVariableScope = new VariableScope(closureVariableScope.parent)

        // Only those local variables not overridden in closure scope
        closureVariableScope.getReferencedLocalVariablesIterator().each { VariableExpression local ->
            if (!mapExpression.mapEntryExpressions.any { it.keyExpression.value == local.name }) {
                 newVariableScope.putReferencedLocalVariable(local)
            }
        }

        // Copying all class variables
        closureVariableScope.getReferencedClassVariablesIterator().each { FieldNode global ->
            newVariableScope.putReferencedClassVariable(global)
        }


        // Changing source scope to the new one
        closureExpression.variableScope = newVariableScope

    }


}
