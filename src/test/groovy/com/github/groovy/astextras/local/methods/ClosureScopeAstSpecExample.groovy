package com.github.groovy.astextras.local.methods

import groovy.transform.ASTTest
import org.codehaus.groovy.control.CompilePhase

@ASTTest(phase = CompilePhase.CANONICALIZATION, value = {
    assert node.name == 'com.github.groovy.astextras.local.methods.ClosureScopeAstSpecExample'
    assert node.methods.any { it.name == 'proof' }
})
class ClosureScopeAstSpecExample {

    Integer globalVariable = 1

    def check(Map map, Closure execution) {
        execution.delegate = map
        execution.owner = this
        execution()
    }

    @ClosureScope
    def proof() {
        def x = 2 // DeclarationExpression
        def z = 3
        def w = 5
        def result = check(x: 5, z: 10) { // DeclarationExpression
            x + 10 + z + w + globalVariable
        }

        return result // ReturnStatement
    }

}


