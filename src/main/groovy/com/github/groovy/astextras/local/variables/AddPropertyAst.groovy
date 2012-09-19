package com.github.groovy.astextras.local.variables

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase

@GroovyASTTransformation(phase=CompilePhase.INSTRUCTION_SELECTION)
class AddPropertyAst implements ASTTransformation{

	void visit(ASTNode[] astNodes,SourceUnit sourceUnit){

		if (!astNodes) return
		if (!astNodes[0] || !astNodes[1]) return
		if (!(astNodes[0] instanceof AnnotationNode)) return
		if (astNodes[0].classNode?.name != AddProperty.class.name) return

		ClassNode declaringClass = astNodes[1]	
		AnnotationNode annotation = astNodes[0]
		String propertyName = annotation.members['name'].text
		Class propertyType = annotation.members['type'].type.typeClass

		def newProperty = new AstBuilder().buildFromSpec {
			propertyNode(
				"${propertyName}",
				ClassNode.ACC_PUBLIC,
				propertyType, 
				this.class,
				{}
			)
		}

		declaringClass.addProperty(newProperty[0])
	}


}
