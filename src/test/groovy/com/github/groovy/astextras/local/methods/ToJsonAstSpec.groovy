package com.github.groovy.astextras.local.methods

import spock.lang.Specification
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

class ToJsonAstSpec extends Specification{

	def "Testing ToJSon functionality"(){
		setup: "Enabling transformation"
			def invoker = new TransformTestHelper(
				new ToJsonAst(),
				CompilePhase.INSTRUCTION_SELECTION
			)	
			def file = new File(
				"./src/test/groovy/com/github/groovy/astextras/local/methods/Silly.groovy"
			)
			def clazz = invoker.parse(file)
			def annotatedInstance = clazz.newInstance()
			def nested = clazz.newInstance()
		when: "Invoking toJson"
			nested.country = "spain"
			annotatedInstance.name = "john"
			annotatedInstance.country = "ireland"
			annotatedInstance.child = nested 
		then: "The expected values shoud be given"
			annotatedInstance.properties
 			annotatedInstance.toJson() == '{"child":{"country":"spain"},"country":"ireland","name":"john"}'
	}
}
