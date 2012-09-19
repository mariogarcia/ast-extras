package com.github.groovy.astextras.local.variables

import java.lang.annotation.*
import org.codehaus.groovy.transform.GroovyASTTransformationClass


@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.TYPE])
@GroovyASTTransformationClass(["com.github.groovy.astextras.local.variables.AddPropertyAst"])
public @interface AddProperty{
	String name()
	Class type()
}
