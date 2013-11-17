package com.github.groovy.astextras.local.inner


class MoveToInnerAstTests extends GroovyTestCase {

    void testTransformation() {
        // given:
            def script = '''
                import com.github.groovy.astextras.local.inner.MoveToInner

                class Something {
                    @MoveToInner("Foo")
                    def myMethod() {
                        'Hey'
                    }
                    def doSomething() {
                        new Foo()
                    }
                }
            '''
        // when:
           def clazz = new GroovyClassLoader().parseClass(script)
        // then:
           assert clazz.newInstance().doSomething()
    }

}
