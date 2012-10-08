package com.github.groovy.astextras.local.main

import com.github.groovy.astextras.test.AstBaseSpec

class MainASTSpec extends AstBaseSpec{
	
	def "Testing Main transformation"(){
		setup: "Enabling transformation"
			def annotatedInstance = getClassToTest(MainAST).newInstance() 
		when: "Trying to call the method added by our AST"
			annotatedInstance.main(null)
		then: "No exception is thrown"
			true
	}
}
