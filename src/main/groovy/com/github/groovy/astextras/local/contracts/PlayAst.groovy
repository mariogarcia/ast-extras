package com.github.groovy.astextras.local.contracts

import com.github.groovy.astextras.local.base.*

import java.lang.annotation.*
import org.codehaus.groovy.transform.GroovyASTTransformationClass

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
class PlayAst extends ExceptionFriendlyAst{

	static final PLAY_METHOD_NAME = "play"
	static final PLAY_METHOD_PARAM_NAME = "params"

 /* We need to inject a DataFlows instance in a variable called "flow" */
	void processNodes(ASTNode[] astNodes,SourceUnit sourceUnit){
	 /* Checking constraints */
		if (!astNodes) return
		if (!astNodes[0] || !astNodes[1]) return
		if (!(astNodes[0] instanceof AnnotationNode)) return
		if (astNodes[0].classNode?.name != Play.class.name) return
		if (!(astNodes[1] instanceof MethodNode)) return
     /* Assigning */
		MethodNode methodNode = astNodes[1]
		AnnotationNode annotationNode = astNodes[0]
		ClassNode theOtherClass = annotationNode.getMember("value").type
	 /* Getting the other class execute's method code */
		def paramArray = [new Parameter(new ClassNode(Object),PLAY_METHOD_PARAM_NAME)] as Parameter[]
		def playMethodExists = theOtherClass.hasMethod(PLAY_METHOD_NAME,paramArray)
		def playMethod = theOtherClass.getMethod(PLAY_METHOD_NAME,paramArray)
	 /* If the method is not void we will return an error */
		if (!playMethod || !playMethod.isVoidMethod()){
			throw new Exception("Check that the play method exists and its return type is void")
		}
		def firstCode2Add = playMethod.code
	 /* Getting the former code */	
		Statement formerCode = methodNode.getCode()
	 /* Building the new method code */
		Statement newCode = new AstBuilder().buildFromSpec{
			block{
	 		 /* Adding first the first code to add :S */
				if (firstCode2Add instanceof BlockStatement){
				 /* Getting all the statements inside the block statement and 
					apply them to the final code */
					firstCode2Add.statements.each{st->
						expression.add st 
					}
				} else {
				 /* Still don't know why sometimes it is a returnStatement when 
					the method is always void */
					expression.add firstCode2Add
				}
	 		 /* Adding the rest of the code */
				expression.add formerCode
			}
		}.find{it}
	 /* Now we put the new code */
		methodNode.setCode(newCode)
	 /* Cleaning up the scopes, otherwise the compiler complains with a NPE. I'm not
		sure but I think it's because of the closure scope  */
		VariableScopeVisitor scopeVisitor = new VariableScopeVisitor(sourceUnit)
		scopeVisitor.visitClass(methodNode.declaringClass)	
	}

}
