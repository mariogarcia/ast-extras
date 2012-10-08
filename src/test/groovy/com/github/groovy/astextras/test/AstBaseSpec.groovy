package com.github.groovy.astextras.test

import spock.lang.Specification
import org.codehaus.groovy.tools.ast.TransformTestHelper

import static org.codehaus.groovy.control.CompilePhase.INSTRUCTION_SELECTION

/**
*
* @author marioggar
**/
class AstBaseSpec extends Specification{

	static final BASE = "./src/test/groovy/"

	/**
	 * This method helps to create a new class instance to be able to 
	 * test the class that uses the transformation
	 * 
	 * @param transformationClass The name of the AST transformation we want to test
	 * @return a class of the class that contains the transformation
	**/
	def getClassToTest(transformationClass){
		def invoker = new TransformTestHelper(
			transformationClass.newInstance(),
			INSTRUCTION_SELECTION
		)	
		def qualifiedName = getClass().name.replaceAll("\\.","\\/")
		def file = new File("${BASE}${qualifiedName}Example.groovy")
	 /* The class we want to test */
		invoker.parse(file)
	}

}
