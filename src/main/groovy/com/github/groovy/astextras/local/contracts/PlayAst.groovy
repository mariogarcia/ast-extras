package com.github.groovy.astextras.local.contracts

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
class PlayAst implements ASTTransformation{

 /* We need to inject a DataFlows instance in a variable called "flow" */
	void visit(ASTNode[] astNodes,SourceUnit sourceUnit){
	 /* Checking constraints */
		if (!astNodes) return
		if (!astNodes[0] || !astNodes[1]) return
		if (!(astNodes[0] instanceof AnnotationNode)) return
		if (astNodes[0].classNode?.name != Play.class.name) return
		if (!(astNodes[1] instanceof MethodNode)) return
     /* Assigning */
		MethodNode methodNode = astNodes[1]
		AnnotationNode annotationNode = astNodes[0]
		ClassNode theOtherClass = annotationNode.members?.getAt('value').type
	 /* Getting the other class execute's method code */
		def parameters = [] as Parameter[]
		MethodNode executeMethod = theOtherClass.getMethod("execute",parameters)
		Statement firstCode2Add = executeMethod.getCode()
	 /* Getting the former code */	
		Statement formerCode = methodNode.getCode()
	 /* Building the new method code */
		Statement newCode = new AstBuilder().buildFromSpec{
			block{
	 		 /* Adding first the first code to add :S */
				expression.add firstCode2Add	
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
