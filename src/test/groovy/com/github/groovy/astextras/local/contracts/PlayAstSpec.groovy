package com.github.groovy.astextras.local.contracts

import com.github.groovy.astextras.test.AstBaseSpec

class PlayAstSpec extends AstBaseSpec {

	def "Testing the contract annotations"(){
		setup: "Creating an instance to test"
			def instanceToTest = getClassToTest(PlayAst).newInstance()
		when: "Executing the method annotated with @Play"
			def result = instanceToTest.mySimpleMethod()
		then: "We should expect the following message"
			result == "This is cool!!! isn't?"
	}

}
