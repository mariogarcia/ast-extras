package com.github.groovy.astextras.local.inner

class MoveToInnerAstSpecExample {

    @MoveToInner('Foo')
    def myMethod() {
        'it worked!'
    }

}
