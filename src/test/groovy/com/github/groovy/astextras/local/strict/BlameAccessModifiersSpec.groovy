package com.github.groovy.astextras.local.strict

import com.github.groovy.astextras.test.AstBaseSpec

class BlameAccessModifiersSpec extends AstBaseSpec {

    def 'Throwing an exception when finding an access modifier'() {
        when: "Enabling transformation"
			def classToTest = getClassToTest(BlameAccessModifiers)
        and: 'Creating an instance of the annotated class'
			classToTest.newInstance()
        and: 'An exception should be thrown '
            e = thrown(Exception)
        then:
            e.message == 'Dont use them'
    }

}
