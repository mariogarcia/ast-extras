package com.github.groovy.astextras.local.gpars

/**
 * This class shows how to use the @WithPool transformation
**/
class Silly{

   /**
	* Sometimes is just annoying to use the GPars.withPool expression over and
	* over through methods. Using the annotation the method block is wrapped
	* with a GParsPool.withPool{...} code
	**/
	@WithPool
	def someParallelExecution(){
		[1,2,3,4,5].collectParallel{it*2}
	}

   /**
	* Here we can put the required number of threads
	**/
	@WithPool(5)
	def someParallelExecutionWithNumberOfThreads(){
		[1,2,3,4,5].collectParallel{it*2}
	}
}
