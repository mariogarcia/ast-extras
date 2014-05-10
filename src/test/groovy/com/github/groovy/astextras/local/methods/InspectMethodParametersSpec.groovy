package com.github.groovy.astextras.local.methods

import com.github.groovy.astextras.test.AstBaseSpec

import spock.lang.Specification
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

class InspectMethodParametersSpec extends AstBaseSpec{

    def 'Inspecting which parameters a method has'() {
        when: 'Building an instance of the example'
            def classToTest = getClassToTest(InspectMethodParametersAst)
			def annotatedInstance = classToTest.newInstance()
            def parametersNames = annotatedInstance.tellmeParameters()
        then: 'At least we should be able to get the parameter names'
            ['x','y'].every{ it in parametersNames }
        and: 'Of course the method executes as expected'
            annotatedInstance.tellme(1,2) == 3
    }

}
