package com.github.groovy.astextras.global.macro

class MacroExpandAstSpecExample {

    Integer something() {
        return let(
            [a: 1,
             b: { a -> a + 1}],
             { a * b})
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
