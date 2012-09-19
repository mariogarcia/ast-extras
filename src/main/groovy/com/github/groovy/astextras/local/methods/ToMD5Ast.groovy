package com.github.groovy.astextras.local.methods

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit


@GroovyASTTransformation(phase=CompilePhase.INSTRUCTION_SELECTION)
class ToMD5Ast implements ASTTransformation{

	void visit(ASTNode[] astNodes,SourceUnit sourceUnit){
		if (!astNodes) return
		if (!astNodes[0] || !astNodes[1]) return
		if (!(astNodes[0] instanceof AnnotationNode)) return
		if (astNodes[0].classNode?.name != ToMD5.class.name) return

		def fieldNode = astNodes[1]
		def propertyName = fieldNode.name
		def annotationNode = astNodes[0]
		def classNode = fieldNode.declaringClass
		def newMethodName =  "${propertyName}ToMD5"
		def newMethod = new AstBuilder().buildFromSpec{
			method newMethodName, ClassNode.ACC_PUBLIC, String,{
				parameters{}
				exceptions{}
				block{
					expression{
						methodCall{
							methodCall{
								methodCall{
									staticMethodCall(java.security.MessageDigest,"getInstance"){
										argumentList { constant "MD5" }
									}
									constant "digest"
									argumentList{
										methodCall{
											variable "${propertyName}"
											constant "getBytes"
											argumentList {
												constant "UTF-8"
											}
										}
									}
								}
								constant "encodeHex"
								argumentList {}
							}
							constant "toString"
							argumentList {}
						}
					}
				}	
				annotations{}
			}
		}	
		classNode.addMethod(newMethod[0])
	}
}
