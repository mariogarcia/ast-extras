package com.github.groovy.astextras.local.methods

import java.lang.annotation.*
import org.codehaus.groovy.transform.GroovyASTTransformationClass

@Target([ElementType.FIELD])
@Retention(RetentionPolicy.SOURCE)
@GroovyASTTransformationClass(["com.github.groovy.astextras.local.methods.ToMD5Ast"])
@interface ToMD5{ }
