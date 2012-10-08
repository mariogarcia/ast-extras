package com.github.groovy.astextras.local.gpars

import org.codehaus.groovy.classgen.*
import org.codehaus.groovy.syntax.SyntaxException

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.builder.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase

@GroovyASTTransformation(phase=CompilePhase.INSTRUCTION_SELECTION)
class FlowAst implements ASTTransformation{

 /* We need to inject a DataFlows instance in a variable called "flow" */
	void visit(ASTNode[] astNodes,SourceUnit sourceUnit){
	 /* Checking constraints */
		if (!astNodes) return
		if (!astNodes[0] || !astNodes[1]) return
		if (!(astNodes[0] instanceof AnnotationNode)) return
		if (astNodes[0].classNode?.name != Flow.class.name) return
		if (!(astNodes[1] instanceof MethodNode)) return
     /* Assigning */
		MethodNode methodNode = astNodes[1]
		AnnotationNode annotationNode = astNodes[0]
	 /* Getting the former code */	
		Statement formerCode = methodNode.getCode()
		Statement newCode = new AstBuilder().buildFromSpec{
			block{
			  /* Create a new instance of DataFlows "flow" */	
				expression{
					declaration{
						variable "flow"
						token "="
						constructorCall(groovyx.gpars.dataflow.Dataflows){
							argumentList{}
						}
					}
				}
				expression.add formerCode
			}
		}.find{it}
	 /* Setting the new code */
		methodNode.setCode(newCode)
		VariableScopeVisitor scopeVisitor = new VariableScopeVisitor(sourceUnit)
		scopeVisitor.visitClass(methodNode.declaringClass)
	}
} 
