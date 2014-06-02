package com.github.groovy.astextras.local.methods

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.expr.Expression
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

import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase
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
        Statement interestingStatement =
            methodCode.statements.findAll { stmt ->
                (stmt instanceof ExpressionStatement) &&
                (stmt.expression instanceof DeclarationExpression)
            }.last() // adhoc

        // Decomposing 'def result = check(...){}'
        DeclarationExpression expression    = interestingStatement.expression
        VariableExpression leftSide         = expression.variableExpression
        MethodCallExpression rightSide      = expression.rightExpression

        ArgumentListExpression arguments    = rightSide.arguments
        MapExpression mapExpression         = arguments.expressions.first()
        ClosureExpression closureExpression = arguments.expressions.last()

        ConstantExpression ve = mapExpression.mapEntryExpressions.valueExpression.first()

        println ve.value
        //ONCE WE'VE CHANGED ARGUMENTS
        //arguments.arguments = [changedMap, changedClosure]

    }

}
