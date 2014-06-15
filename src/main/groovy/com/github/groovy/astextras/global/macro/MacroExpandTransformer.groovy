package com.github.groovy.astextras.global.macro

import org.codehaus.groovy.ast.ClassCodeExpressionTransformer
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.VariableScope
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.classgen.VariableScopeVisitor
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.syntax.Types
import org.codehaus.groovy.transform.AbstractASTTransformUtil

/**
 * Created by mario on 15/06/14.
 */

class MacroExpandTransformer extends ClassCodeExpressionTransformer {

    private SourceUnit sourceUnit

    MacroExpandTransformer(SourceUnit sourceUnit) {
        this.sourceUnit = sourceUnit
    }


    public Expression transform(Expression expression) {
        if (expression instanceof MethodCallExpression && expression.methodAsString == 'let') {
            StaticMethodCallExpression exp = new StaticMethodCallExpression(
               ClassHelper.make(MacroUtils, false),
               'bind',
                new ArgumentListExpression(//value, fn
                   new ConstantExpression(3),
                   new ClosureExpression(
                      [new Parameter(ClassHelper.Integer_TYPE,'x')] as Parameter[],
                      new BlockStatement(
                         [new ExpressionStatement(new VariableExpression('x'))] as Statement[],
                         new VariableScope()
                      )
                   )
                )
            )

            VariableScopeVisitor scopeVisitor = new VariableScopeVisitor(sourceUnit)
            scopeVisitor.visitStaticMethodCallExpression(exp)
            return exp

        }
        return expression.transformExpression(this)
    }

    SourceUnit getSourceUnit() {
        return this.sourceUnit
    }
}
