package com.github.groovy.astextras.local.methods

class InspectMethodParametersSpecExample {

    @InspectMethodParameters
    Integer tellme(Integer x, Integer y) {
        return x + y
    }

    // Map getTellmeParameters()

}
