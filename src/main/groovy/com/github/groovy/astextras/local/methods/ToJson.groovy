package com.github.groovy.astextras.local.methods

import java.lang.annotation.Target
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import org.codehaus.groovy.transform.GroovyASTTransformationClass

/**
 * This annotation can be used by any type to output the values of its properties
 * in a JSON tree structure
**/
@Target([ElementType.TYPE])
@Retention(RetentionPolicy.SOURCE)
@GroovyASTTransformationClass(["com.github.groovy.astextras.local.methods.ToJsonAst"])
@interface ToJson{ }
