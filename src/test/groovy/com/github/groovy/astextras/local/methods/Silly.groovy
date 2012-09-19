package com.github.groovy.astextras.local.methods

/**
 * All transformation added to this class add methods to the bytecode
**/
@ToJson()
class Silly{
 /* nameToMD5() */
	@ToMD5()
	String name
	String country

	Silly child
}
