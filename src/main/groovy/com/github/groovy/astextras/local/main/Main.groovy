package com.github.groovy.astextras.local.main

import java.lang.annotation.*
import org.codehaus.groovy.transform.GroovyASTTransformationClass

@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.METHOD])
@GroovyASTTransformationClass(["com.github.groovy.astextras.local.main.MainAST"])
public @interface Main{ }
