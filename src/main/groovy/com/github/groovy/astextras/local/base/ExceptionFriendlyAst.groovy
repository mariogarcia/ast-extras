package com.github.groovy.astextras.local.base

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.transform.*
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase

abstract class ExceptionFriendlyAst implements ASTTransformation{

	void visit(ASTNode[] astNodes,SourceUnit sourceUnit){
		try{
			processNodes(astNodes,sourceUnit)
		} catch (e){
			sourceUnit.addException(e)
		}
	}

	public abstract void processNodes(ASTNode[] astNodes,SourceUnit sourceUnit)
} 
