package com.github.groovy.astextras.local.methods

import spock.lang.Specification
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

class ToMD5AstSpec extends Specification{

	def "Testing adding new method"(){
		setup: "Enabling transformation"
			def invoker = new TransformTestHelper(
				new ToMD5Ast(),
				CompilePhase.INSTRUCTION_SELECTION
			)	
			def file = new File(
				"./src/test/groovy/com/github/groovy/astextras/local/methods/Silly.groovy"
			)
			def clazz = invoker.parse(file)
			def annotatedInstance = clazz.newInstance()
		when: "Trying to invoke the added method"
			annotatedInstance.name = "mario"
		then: "No exception is thrown"
			annotatedInstance.metaClass.respondsTo(annotatedInstance,"nameToMD5")
			annotatedInstance.nameToMD5() == "de2f15d014d40b93578d255e6221fd60"
	}

}
