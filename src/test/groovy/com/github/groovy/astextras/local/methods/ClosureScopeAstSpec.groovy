package com.github.groovy.astextras.local.methods

import com.github.groovy.astextras.test.AstBaseSpec

import java.lang.Void as SHOULD
import spock.lang.Specification
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

class ClosureScopeAstSpec extends AstBaseSpec {

    SHOULD 'Return the expected result for the lexical scope'() {
        given: 'The instance of the transformation example'
            def classToTest = getClassToTest(ClosureScopeAst)
			def instance = classToTest.newInstance()
        when: 'Executing the example'
            def result = instance.proof()
        then: 'The result should not use the local variable value'
            result == 25
    }


}


