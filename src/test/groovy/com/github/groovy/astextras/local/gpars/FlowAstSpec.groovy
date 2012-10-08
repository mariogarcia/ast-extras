package com.github.groovy.astextras.local.gpars

import com.github.groovy.astextras.test.AstBaseSpec

class FlowAstSpec extends AstBaseSpec{

	def "Testing executing parallel tasks"(){
		setup: "Creating an instance to test"
			def instanceToTest = getClassToTest(FlowAst).newInstance()
		when: "Executing the method annotated with Flow"
			def result = instanceToTest.executeParallelTasks()
		then: "The final result should be the expected"
			result.value == 230 
	}
}
