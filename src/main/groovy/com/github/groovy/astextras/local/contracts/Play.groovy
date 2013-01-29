package com.github.groovy.astextras.local.contracts

import java.lang.annotation.*
import org.codehaus.groovy.transform.GroovyASTTransformationClass

/**
*
**/
@Target([ElementType.METHOD])
@Retention(RetentionPolicy.SOURCE)
@GroovyASTTransformationClass(["com.github.groovy.astextras.local.contracts.PlayAst"])
@interface Play{
	Class value()
}
