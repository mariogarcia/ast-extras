package com.github.groovy.astextras.local.inner

import spock.lang.Specification
import org.codehaus.groovy.control.CompilerConfiguration

class MoveToInnerAstSpec extends Specification {

    def 'Testing how to generate an inner class'() {
        given:
            def configuration = new CompilerConfiguration()
            configuration.setTargetDirectory(new File('build/classes/test/'))
            def myClassLoader =
                new GroovyClassLoader(
                    this.getClass().getClassLoader(),
                    configuration
                )
            def script = '''
                package com.github.groovy.astextras.local.inner

                class Something {
                    @MoveToInner("Foo")
                    def myMethod() {
                        'Hey John'
                    }
                }
            '''
        when:
           def clazz = myClassLoader.parseClass(script)

           def somethingClazz =
            myClassLoader.parseClass('''
                package com.github.groovy.astextras.local.inner

                class Hey {
                    def doSomething() {
                        new Something.Foo().myMethod()
                    }
                }

            ''')
        then:
           somethingClazz.newInstance().doSomething() == 'Hey John'
    }

}
