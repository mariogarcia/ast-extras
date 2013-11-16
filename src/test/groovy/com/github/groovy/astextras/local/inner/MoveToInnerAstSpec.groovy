package com.github.groovy.astextras.local.inner

import static org.codehaus.groovy.control.CompilePhase.*

import com.github.groovy.astextras.test.AstBaseSpec

class MoveToInnerAstSpec extends AstBaseSpec {

    def "Testing transformation"() {
        setup: 'Enabling transformation'
            def annotatedInstance =
                getClassToTestForPhase(
                    MoveToInnerAst,
                    INSTRUCTION_SELECTION
                ).
            newInstance()
        expect: 'Trying to call the method in the inner class'
            true
    }

}
