package com.github.groovy.astextras.local.contracts

/**
 * This class has a method annotated with a couple of @Play annotations.
 * The former helps the latter to know if it should be executed or not
**/
class PlayAstSpecExample {

	@Play(PreConditionAction)
	def mySimpleMethod(){
		message
	}
}

/**
 * This action exposes a given result number
**/
class PreConditionAction{
	def execute(){
		def message = "This is cool!!! isn't?"
	}
}
