package com.github.groovy.astextras.local.variables

import spock.lang.Specification
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

class AddPropertyAstSpec extends Specification{

	def "Testing property has been added to the annotated class"(){
		setup: "Enabling transformation"
			def invoker = new TransformTestHelper(
				new AddPropertyAst(),
				CompilePhase.INSTRUCTION_SELECTION
			)	
			def file = new File(
				"./src/test/groovy/com/github/groovy/astextras/local/variables/Silly.groovy"
			)
			assert file
			def clazz = invoker.parse(file)
			assert clazz
			def annotatedInstance = clazz.newInstance()
		when: "Trying to assign some value to the added property"
			annotatedInstance.customProperty= 10
		then: "I should be able to recover the value"
			annotatedInstance.customProperty == 10
	}



}
