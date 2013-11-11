package com.github.groovy.astextras.local.strict

/**
 * When trying to execute this code the compiler will throw an
 * error saying that we shouldnt be using access modifiers
 */
@BlameAccessModifiers
class BlameAccessModifiersSpecSample {

    private void doSomething() {
        println 'Groovy doesnt care about access modifiers'
    }

}
