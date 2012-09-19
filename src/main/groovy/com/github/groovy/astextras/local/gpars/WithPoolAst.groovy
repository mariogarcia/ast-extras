package com.github.groovy.astextras.local.gpars

import groovyx.gpars.GParsPool

import org.codehaus.groovy.classgen.*
import org.codehaus.groovy.syntax.SyntaxException


import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.builder.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase

/**
 * This transformation wraps all the statements inside a method into a 
 * GParsPool.withPool{} closure. This way you can only care about the internals
 * of your method.
**/
@GroovyASTTransformation(phase=CompilePhase.INSTRUCTION_SELECTION)
class WithPoolAst implements ASTTransformation{

 /* Wrapping all the statements into a withPool closure */
	void visit(ASTNode[] astNodes,SourceUnit sourceUnit){
		if (!astNodes) return
		if (!astNodes[0] || !astNodes[1]) return
		if (!(astNodes[0] instanceof AnnotationNode)) return
		if (astNodes[0].classNode?.name != WithPool.class.name) return
		if (!(astNodes[1] instanceof MethodNode)) return
	 /* Getting the annotated method and the annotation parameters if any*/
		MethodNode methodNode = astNodes[1]
		AnnotationNode annotationNode = astNodes[0]
		Integer numberOfThreads = annotationNode.members?.getAt('value')?.text
	 /* If method node is of type void then we should stop compilation */
		if (methodNode.returnType.typeClass == void)
			sourceUnit.addError(createSyntaxExceptionWhenVoidMethod(methodNode.name))
	 /* Taking the former code to add it later on inside the withPool closure */
		Statement formerCode = methodNode.getCode()
		Statement newCode = new AstBuilder().buildFromSpec{
			block{
				returnStatement{
					staticMethodCall(GParsPool,"withPool"){
						argumentList{
						 /* If user has established the required number of
							threads it's passed as first argument */
							if (numberOfThreads){
								constant numberOfThreads
							}
						 /* Closure block */
							closure{
								parameters{
									parameter 'pool' : Object
								}
								block{
									expression.add formerCode
								}
							}	
						}
					}
				}
			}
		}.find{it}
	 /* Now we put the new code */
		methodNode.setCode(newCode)
	 /* Cleaning up the scopes, otherwise the compiler complains with a NPE. I'm not
		sure but I think it's because of the closure scope  */
		VariableScopeVisitor scopeVisitor = new VariableScopeVisitor(sourceUnit)
		scopeVisitor.visitClass(methodNode.declaringClass)
	}

   /**
	* It has no sense to use this transformation with a void method. So when
	* this happens it would throw a SyntaxException to show the error.
	**/
	def createSyntaxExceptionWhenVoidMethod(methodName){
		new SyntaxException(
			"Why are you using WithPool with a method of type void (method $methodName)?",0,0
		)
	}
}
