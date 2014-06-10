package com.github.groovy.astextras.global.macro

import org.codehaus.groovy.control.SourceUnit

import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassCodeVisitorSupport

import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MapExpression
import org.codehaus.groovy.ast.expr.ClassExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ExpressionTransformer

class MacroExpandVisitor extends ClassCodeVisitorSupport {

    SourceUnit sourceUnit

    MacroExpandVisitor(SourceUnit sourceUnit) {
       this.sourceUnit = sourceUnit
    }

    SourceUnit getSourceUnit() {
        return this.sourceUnit
    }

    void visitMethodCallExpression(MethodCallExpression expression) {
        if (expression.name == 'let') {
            expression.transformExpression(new ExpressionTransformer() {
                Expression transform(Expression initialExpression) {
                    // OLD
                    MethodCallExpression methodCallExpression = (MethodCallExpression)
                    initialExpression
                    ArgumentListExpression argumentExpressions = methodCallExpression.arguments.expressions
                    MapExpression mapExpression = argumentExpressions.first()
                    ClosureExpression closureExpression = argumentExpressions.last()
                    // NEW
                    return binding(mapExpression, closureExpression)
                }
            })
        }
    }

    void binding(MapExpression mapExpression, ClosureExpression fn) {
       new MethodCallExpression(
            new ClassExpression(ClassHelper.make(MacroUtils)),
            'binding',
            binding(
                mapExpression.expressions.first().valueExpression,
                new ClosureExpression(
                    new Parameter() [mapExpression.expressions.first().keyExpression],
                    mapExpressions.size() > 1 ?
                        binding(new MapExpression(mapExpression.expressions.last()), fn) :
                        fn
                )
            )
       )
    }

}
