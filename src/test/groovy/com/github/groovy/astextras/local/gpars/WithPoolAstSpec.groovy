package com.github.groovy.astextras.local.gpars

import spock.lang.Specification
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

/**
 * @author marioggar
**/
class WithPoolAstSpec extends Specification{

	def "Testing a method annotated with WithPool"(){
		setup: "Enabling transformation"
			def invoker = new TransformTestHelper(
				new WithPoolAst(),
				CompilePhase.INSTRUCTION_SELECTION
			)	
			def file = new File(
				"./src/test/groovy/com/github/groovy/astextras/local/gpars/Silly.groovy"
			)
			def clazz = invoker.parse(file)
			def annotatedInstance = clazz.newInstance()
		when: "Executing some parallel execution"
			def result = annotatedInstance.someParallelExecution()
		then: "It should return the expected array"
			result == [2,4,6,8,10]
	}
}
