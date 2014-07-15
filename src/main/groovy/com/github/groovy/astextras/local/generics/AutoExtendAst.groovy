package com.github.groovy.astextras.local.generics

import static org.codehaus.groovy.ast.ClassHelper.make

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.GenericsType
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.codehaus.groovy.transform.AbstractASTTransformation

import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.CompilePhase

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
class AutoExtendAst extends AbstractASTTransformation {

    void visit(ASTNode[] nodes, SourceUnit unit) {
        if (nodes?.length != 2) return
        if (!(nodes[0] instanceof AnnotationNode)) return
        if (!(nodes[1] instanceof ClassNode)) return
        if (nodes[0].classNode.name != AutoExtend.name) return

        AnnotationNode annotationNode = nodes[0]
        ClassNode classNode = nodes[1]
        ClassNode genericClass = make(annotationNode.getMember('value').text)
        ClassNode class2Extend = make(A, true)

        class2Extend = class2Extend.plainNodeReference
        class2Extend.genericsTypes = [ new GenericsType(make(C)) ] as GenericsType[]

        classNode.superClass = class2Extend
        classNode.setUsingGenerics(true)
    }


}
