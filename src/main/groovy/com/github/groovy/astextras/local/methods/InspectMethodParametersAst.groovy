package com.github.groovy.astextras.local.methods

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase

/**
 * @author marioggar
 *
**/
@GroovyASTTransformation(phase=CompilePhase.INSTRUCTION_SELECTION)
class InspectMethodParametersAst implements ASTTransformation{

	void visit(ASTNode[] astNodes, SourceUnit sourceUnit){
	 /* Some checkings */
		if (!astNodes) return
		if (!astNodes[0] || !astNodes[1]) return
		if (!(astNodes[0] instanceof AnnotationNode)) return
		if (astNodes[0].classNode?.name != InspectMethodParameters.class.name) return
		if (!(astNodes[1] instanceof MethodNode)) return
	 /* Establishing what the declaring class is */
		MethodNode declaringMethod = astNodes[1]
        ClassNode classNode = declaringMethod.declaringClass
        String parametersMethodName = "${declaringMethod.name}Parameters"

        def methodExposingParameters = new AstBuilder().buildFromSpec {
            method parametersMethodName, ClassNode.ACC_PUBLIC, Object, {
                parameters {}
                exceptions {}
                block {
                    returnStatement {
                        map {
                            mapEntry {
                                constant 'x'
                                constant 1
                            }
                            mapEntry {
                                constant 'y'
                                constant 2
                            }
                        }
                    }
                }
                annotations {}
            }
	    }
        classNode.addMethod(methodExposingParameters[0])
    }

}


