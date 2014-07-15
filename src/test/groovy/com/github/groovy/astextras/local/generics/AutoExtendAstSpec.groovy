package com.github.groovy.astextras.local.generics

import com.github.groovy.astextras.test.AstBaseSpec

class AutoExtendAstSpec extends AstBaseSpec{

	void "Testing generics generation"(){
		setup: "Creating an instance to test"
			def instanceToTest =
                getClassToTest(AutoExtendAst)
                    .newInstance(innerOther: new C())
        expect: 'The inheritance worked as a charm'
            instanceToTest.innerOther.name == 'CRULE'
    }

}

