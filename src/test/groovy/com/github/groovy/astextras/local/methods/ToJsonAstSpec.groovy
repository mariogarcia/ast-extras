package com.github.groovy.astextras.local.methods

import com.github.groovy.astextras.test.AstBaseSpec

import spock.lang.Specification
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

class ToJsonAstSpec extends AstBaseSpec{

	def "Testing ToJSon functionality"(){
		setup: "Enabling transformation"
			def classToTest = getClassToTest(ToJsonAst)
			def annotatedInstance = classToTest.newInstance() 
			def nested = classToTest.newInstance()
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
