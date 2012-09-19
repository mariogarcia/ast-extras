package com.github.groovy.astextras.local.gpars

import java.lang.annotation.*
import org.codehaus.groovy.transform.GroovyASTTransformationClass

/**
 * Sometimes is annoying to write a a method that returns the result of a
 * GParsPool.withPool{} expression. wouldn't be easier to write the method
 * block without the need of wrapping it inside that expression? 
 * 
 * All methods annotated with WithPool will wrap their block code inside
 * a GParsPool.withPool{} expression.
 * 
 * You can establish the number of threads needed for the current execution.
 *
**/
@Target([ElementType.METHOD])
@Retention(RetentionPolicy.SOURCE)
@GroovyASTTransformationClass(["com.github.groovy.astextras.local.gpars.WithPoolAst"])
public @interface WithPool{
	int value() default 0
}
