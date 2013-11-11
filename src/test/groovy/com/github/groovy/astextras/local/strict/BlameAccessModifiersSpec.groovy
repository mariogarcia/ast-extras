package com.github.groovy.astextras.local.strict

import static org.codehaus.groovy.control.CompilePhase.INSTRUCTION_SELECTION

import spock.lang.Specification
import org.codehaus.groovy.syntax.SyntaxException
import org.codehaus.groovy.control.CompilationFailedException
import org.codehaus.groovy.tools.ast.TransformTestHelper

class BlameAccessModifiersSpec extends Specification {

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
		def file = new File("${BASE}${qualifiedName}Example.txt")
	 /* The class we want to test */
		invoker.parse(file)
	}

    def 'Throwing an exception when finding an access modifier'() {
        when: "Enabling transformation"
			def annotatedInstance = getClassToTest(BlameAccessModifiersAst).newInstance()
        then:
            thrown(CompilationFailedException)
    }

}
