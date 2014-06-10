package com.github.groovy.astextras.global.macro

import com.github.groovy.astextras.test.AstBaseSpec

import spock.lang.Specification

import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

class MacroExpandAstSpec extends AstBaseSpec {

    void 'Expanding a given expression'() {
        expect: 'An initialized instance'
            def class2Test =
                getClassToTestForPhase(
                    MacroExpandAst,
                    CompilePhase.CANONICALIZATION
                )
            def instance = class2Test.newInstance()
        when: 'Calling the method using the raw expression'
            def result = instance.something()
        then: 'Expecting the macro has done its job'
           result == 3
    }

}


