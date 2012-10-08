package com.github.groovy.astextras.local.gpars

import java.lang.annotation.*
import org.codehaus.groovy.transform.GroovyASTTransformationClass

/**
*
**/
@Target([ElementType.METHOD])
@Retention(RetentionPolicy.SOURCE)
@GroovyASTTransformationClass(["com.github.groovy.astextras.local.gpars.FlowAst"])
@interface Flow{}
