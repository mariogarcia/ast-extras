package com.github.groovy.astextras.local.gpars

import static groovyx.gpars.dataflow.Dataflow.task

class FlowAstSpecExample {

	@Flow
	def executeParallelTasks(){
		task{ flow.a = 10}
		task{ flow.b = 20}
		task{ flow.c = flow.a + flow.b}
		task{ flow.d = flow.a * flow.b}
		task{ flow.e = flow.c + flow.d}
	}
}
