/**
@kind path-problem
*/

import java
import semmle.code.java.dataflow.FlowSources

class ROMethod extends Method{
    ROMethod(){
        (
            this.hasName("readObject") or this.hasName("readExternal")
        )
        and this.getReturnType() instanceof VoidType
        }
}

class Source extends Callable {
    Source(){
        getDeclaringType().getASupertype*() instanceof TypeSerializable and (
            this instanceof ROMethod
        )
    }
}

class NoParaMethod extends Method {
    NoParaMethod() {
        this.hasNoParameters() and this.isPublic() 
        and this.getDeclaringType().getASupertype*() instanceof TypeSerializable
    }
}

// class CallROMethod extends Method {
//     CallROMethod(){
//         exists(Method m|this.polyCalls(m)
//         and m instanceof ReadObjectMethod
//         )    
//         and this.getDeclaringType().getASupertype*() instanceof TypeSerializable
//         and this.getName() != "createApplet"
//         and this.getName() != "createCopy"
//     }
// }

class CallsConstructorMethod extends Method {
    CallsConstructorMethod() {
        exists(Constructor c | 
            this.polyCalls(c)
            and c.getDeclaringType().getASupertype*().hasQualifiedName("java.io", "ObjectInputStream")
            and this.getName() != "createApplet"
            and this.getName() != "createCopy"
            )
    }
}

class SerNoPubC extends Method {
    SerNoPubC() {
        this instanceof CallsConstructorMethod and
        this.getDeclaringType().getASupertype*() instanceof TypeSerializable
        and this.hasNoParameters()
        and this.isPublic()
    }
}


query predicate edges(Method a, Method b) { 
    a.polyCalls(b) 
    and
    (
        b.getDeclaringType().getASupertype*() instanceof TypeSerializable 
        or b.isStatic()
        or b = a.getDeclaringType().getAMethod()
    ) 
 
}

from NoParaMethod source, CallsConstructorMethod sink,Method a,Method b,Method c,Method d
// from NoParaMethod source,CallsConstructorMethod sink
where 
// edges(source, sink)
// edges(source, a) and edges(a, sink)
edges(source, a) and edges(a, b) and edges(b, sink)
// edges(source, a) and edges(a, b) and edges(b,c) and edges(c, sink)
// edges(source, a) and edges(a, b) and edges(b,c) and edges(c, d) and edges(d, sink)


select source, source, sink, "$@ $@ to $@ $@" ,
source.getDeclaringType(),source.getDeclaringType().getName(),
source,source.getName(),
sink.getDeclaringType(),sink.getDeclaringType().getName(),
sink,sink.getName() 
