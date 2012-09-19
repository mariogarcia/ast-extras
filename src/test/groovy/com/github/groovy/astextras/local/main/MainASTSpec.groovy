package com.github.groovy.astextras.local.main

import spock.lang.Specification
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

class MainASTSpec extends Specification{
	
	def "Testing Main transformation"(){
		setup: "Enabling transformation"
			def invoker = new TransformTestHelper(
				new MainAST(),
				CompilePhase.INSTRUCTION_SELECTION
			)
			def file = new File(
				"./src/test/groovy/com/github/groovy/astextras/local/main/Silly.groovy"
			)
			def clazz = invoker.parse(file)
			def annotatedInstance = clazz.newInstance() 
		when: "Trying to call the method added by our AST"
			annotatedInstance.main(null)
		then: "No exception is thrown"
			true
	}
}
