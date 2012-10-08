package com.github.groovy.astextras.local.methods

import com.github.groovy.astextras.test.AstBaseSpec

import spock.lang.Specification
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

class ToMD5AstSpec extends AstBaseSpec{

	def "Testing adding new method"(){
		setup: "Enabling transformation"
			def annotatedInstance = getClassToTest(ToMD5Ast).newInstance()
		when: "Trying to invoke the added method"
			annotatedInstance.name = "mario"
		then: "No exception is thrown"
			annotatedInstance.metaClass.respondsTo(annotatedInstance,"nameToMD5")
			annotatedInstance.nameToMD5() == "de2f15d014d40b93578d255e6221fd60"
	}

}
