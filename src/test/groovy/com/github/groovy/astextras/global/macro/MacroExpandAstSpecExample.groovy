package com.github.groovy.astextras.global.macro

class MacroExpandAstSpecExample {

    Integer something() {
        return let(
            [a: 1],
             { a -> a + 2})
    }

    /*
    bind(
        1,
        { a ->
            bind(
                a + 1,
                { b ->
                    a * b
                }
            )
        }
    )*/

}
