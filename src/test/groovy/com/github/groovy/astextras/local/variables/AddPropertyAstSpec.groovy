package com.github.groovy.astextras.local.variables

import com.github.groovy.astextras.test.AstBaseSpec

import spock.lang.Specification
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

class AddPropertyAstSpec extends AstBaseSpec{

	def "Testing property has been added to the annotated class"(){
		setup: "Enabling transformation"
			def annotatedInstance = getClassToTest(AddPropertyAst).newInstance()
		when: "Trying to assign some value to the added property"
			annotatedInstance.customProperty= 10
		then: "I should be able to recover the value"
			annotatedInstance.customProperty == 10
	}
}
