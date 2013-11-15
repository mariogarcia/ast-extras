package com.github.groovy.astextras.local.inner

import java.lang.annotation.Target
import java.lang.annotation.Retention
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy

import org.codehaus.groovy.transform.GroovyASTTransformationClass

@Target([ElementType.METHOD])
@Retention(RetentionPolicy.SOURCE)
@GroovyASTTransformationClass(["com.github.groovy.astextras.local.inner.MoveToInnerAst"])
public @interface MoveToInner {
    String value()
}
