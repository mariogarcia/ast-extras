package com.github.groovy.astextras.local.main

import org.codehaus.groovy.transform.*
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.ast.builder.AstBuilder

/**
 * This transformation adds a new main method to the annotated
 * class. The given class should be annotated with @Main annotation
**/
@GroovyASTTransformation(phase=CompilePhase.INSTRUCTION_SELECTION)
class MainAST implements ASTTransformation {

 /* Adding a main method to the annotated class */
	void visit(ASTNode[] astNodes, SourceUnit sourceUnit){
		if (!astNodes) return
		if (!astNodes[0] || !astNodes[1]) return
		if (!(astNodes[0] instanceof AnnotationNode)) return
		if (astNodes[0].classNode?.name != Main.class.name) return
		if (!(astNodes[1] instanceof MethodNode)) return


		MethodNode annotatedMethod = astNodes[1]
		ClassNode declaringClass = annotatedMethod.declaringClass
		MethodNode mainMethod = makeMainMethod(annotatedMethod)
		declaringClass.addMethod(mainMethod)
	}

	MethodNode makeMainMethod(MethodNode source){
		def className = source.declaringClass.name
		def methodName= source.name

		def phase = CompilePhase.INSTRUCTION_SELECTION
		def expression = """
			package $source.declaringClass.packageName
	
			class $source.declaringClass.nameWithoutPackage {
				public static void main(String[] args){
					new $className().$methodName()
				}
			}
		"""
		def ast = new AstBuilder().buildFromString(phase,false,expression)
		def methodToAdd = ast[1].methods.find{it.name == 'main'}
		
		methodToAdd
	}	

}
