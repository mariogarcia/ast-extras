package com.github.groovy.astextras.local.gpars

import com.github.groovy.astextras.test.AstBaseSpec

/**
 * @author marioggar
**/
class WithPoolAstSpec extends AstBaseSpec{

	def "Testing a method annotated with WithPool"(){
		setup: "Enabling transformation"
			def annotatedInstance = getClassToTest(WithPoolAst).newInstance()
		when: "Executing some parallel execution"
			def result = annotatedInstance.someParallelExecution()
		then: "It should return the expected array"
			result == [2,4,6,8,10]
	}

	def "Testing sumarizing numbers concurrently"(){
		setup: "Creating a new instance"
			def annotatedInstance = getClassToTest(WithPoolAst).newInstance()
		when: "Executing the annotated method"
			def result = annotatedInstance.sumarizingNumbersConcurrently()	
		then: "Checking result"
			result.intValue() == 15
	}
}
