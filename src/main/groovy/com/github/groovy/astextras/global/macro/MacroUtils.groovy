package com.github.groovy.astextras.global.macro

final class MacroUtils {

    static Object bind(Object value, Closure<?> fn) {
        return fn(value)
    }

    static Object bind(Object value) {
        return value
    }

}
