package com.github.groovy.astextras.local.inner

import com.github.groovy.astextras.test.AstBaseSpec

class MoveToInnerAstSpec extends AstBaseSpec {

    def "Testing transformation"() {
        setup: 'Enabling transformation'
            def annotatedInstance = getClassToTest(MoveToInnerAst).newInstance()
        when: 'Trying to call the method in the inner class'
            def result = annotatedInstance.new Foo().myMethod()
        then: 'We should get the result from the inner class method'
            result == 'it worked!'
    }

}
