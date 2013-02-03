package com.github.groovy.astextras.local.contracts

/**
 * This class has a method annotated with a couple of @Play annotations.
 * The former helps the latter to know if it should be executed or not
**/
class PlayAstSpecExample {

	@Play(PlayAstSpecExample)
	def mySimpleMethod(){
		message
	}

	void play(params){
		def amigo = "Nop"
			println amigo
		def message = "This is cool!!! isn't?"
	}
}

