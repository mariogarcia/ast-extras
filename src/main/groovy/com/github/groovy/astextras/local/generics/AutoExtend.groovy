package com.github.groovy.astextras.local.generics

import java.lang.annotation.Target
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import org.codehaus.groovy.transform.GroovyASTTransformationClass

@Target([ElementType.TYPE])
@Retention(RetentionPolicy.SOURCE)
@GroovyASTTransformationClass(['com.github.groovy.astextras.local.generics.AutoExtendAst'])
@interface AutoExtend {
    Class value()
}

