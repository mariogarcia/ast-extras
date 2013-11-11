package com.github.groovy.astextras.local.strict

import java.lang.annotation.Target
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import org.codehaus.groovy.transform.GroovyASTTransformationClass

@Target([ElementType.METHOD])
@Retention(RetentionPolicy.SOURCE)
@GroovyASTTransformationClass(['com.github.groovy.astextras.local.strict.BlameAccessModifiersAst'])
@interface BlameAccessModifiers {}
