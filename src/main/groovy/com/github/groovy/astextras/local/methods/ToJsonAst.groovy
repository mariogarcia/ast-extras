package com.github.groovy.astextras.local.methods

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase

/**
 * This transformation allows to the annotated class to output its content
 * as a JSON estructure.
 *
 * Methods ToJson() and ToProps() are going to receive an optional parameter
 * with the desired parameters to be serialized to JSON.
 * 
 * @author marioggar
 * 
**/
@GroovyASTTransformation(phase=CompilePhase.INSTRUCTION_SELECTION)
class ToJsonAst implements ASTTransformation{

	void visit(ASTNode[] astNodes, SourceUnit sourceUnit){
	 /* Some checkings */
		if (!astNodes) return
		if (!astNodes[0] || !astNodes[1]) return
		if (!(astNodes[0] instanceof AnnotationNode)) return
		if (astNodes[0].classNode?.name != ToJson.class.name) return
	 /* Establishing what the declaring class is */
		ClassNode declaringClass = astNodes[1]
	 /* Adding the two needed methods */
		declaringClass.addMethod toJsonMethodNode
		declaringClass.addMethod toPropsMethodNode	
	}

   /**
	* This method returns the toJson() statements
	**/
	def getToJsonMethodNode(){
		def statement = new AstBuilder().buildFromString(CompilePhase.INSTRUCTION_SELECTION,true,"""
        		def builder = new groovy.json.JsonBuilder()
        		builder.call(toProps())
        		builder.toString()
		""").find{it}
	 /* The method toJson() throws no exceptions */
		createMethodWithNameAndStatement("toJson",statement)
	}

   /**
	* This method helps to the recursion of nested objects
	*
	* @return a list like structure of the JSON object
	**/
	def getToPropsMethodNode(){
		def statement = new AstBuilder().buildFromString(CompilePhase.INSTRUCTION_SELECTION,true,"""
        		def normalProperties = {it.value && !(it.key in ['class','metaClass'])}
        		def asAMap = {next->
            		next.value.metaClass.methods.find{it.name == 'toJson'} ?
                		[next.key,next.value.toProps()] :
                		[next.key,next.value]
		}
       			properties.findAll(normalProperties).collectEntries(asAMap)
		""").find{it}
	 /* The method toProps() throws no exceptions */
		createMethodWithNameAndStatement("toProps",statement)
	}

   /**
	* This method returns a MethodNode instance representing a method with no
	* parameters and no exceptions
	* 
	* @returns a MethodNode instance
	*
	**/
	def createMethodWithNameAndStatement(name,statement){
		Parameter[] parameters = []
		ClassNode[] exceptions = []
		new MethodNode(
			name,
			ClassNode.ACC_PUBLIC,
			new ClassNode(Object),
			parameters,
			exceptions,
			statement
		)
	}

}
