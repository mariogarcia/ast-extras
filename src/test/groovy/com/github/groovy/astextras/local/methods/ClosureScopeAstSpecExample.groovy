package com.github.groovy.astextras.local.methods

class ClosureScopeAstSpecExample {

    def check(Map map, Closure execution) {
        return map.with(execution)
    }

    @ClosureScope
    def proof() {
        def x = 2 // DeclarationExpression
        def result = check(x: 1) { // DeclarationExpression
            x + 10
        }

        return result // ReturnStatement
    }

}


