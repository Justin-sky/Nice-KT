package com.lj.core.event

interface IEvent {

    fun handle()

    fun handle(a:Any)

    fun handle(a:Any, b:Any)

    fun handle(a:Any, b:Any, c:Any)

    fun handle(a:Any, b:Any, c:Any, d:Any)
}

abstract class AEvent:IEvent{

    abstract fun run()

    override fun handle(){
        this.run()
    }

    override fun handle(a: Any) {
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any, b: Any) {
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any, b: Any, c: Any) {
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any, b: Any, c: Any, d: Any) {
        throw  NotImplementedError("Not yet implemented")
    }
}

abstract class AEvent1<A> : IEvent{

    abstract fun run(a:A)

    override fun handle(){
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any) {
        this.run(a as A)
    }

    override fun handle(a: Any, b: Any) {
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any, b: Any, c: Any) {
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any, b: Any, c: Any, d: Any) {
        throw  NotImplementedError("Not yet implemented")
    }
}


abstract class AEvent2<A, B> : IEvent{

    abstract fun run(a:A, b:B)

    override fun handle(){
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any) {
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any, b: Any) {
        this.run(a as A, b as B)
    }

    override fun handle(a: Any, b: Any, c: Any) {
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any, b: Any, c: Any, d: Any) {
        throw  NotImplementedError("Not yet implemented")
    }
}

abstract class AEvent3<A, B, C> : IEvent{

    abstract fun run(a:A, b:B, c:C)

    override fun handle(){
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any) {
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any, b: Any) {
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any, b: Any, c: Any) {
        this.run(a as A, b as B, c as C)
    }

    override fun handle(a: Any, b: Any, c: Any, d: Any) {
        throw  NotImplementedError("Not yet implemented")
    }
}


abstract class AEvent4<A, B, C, D> : IEvent{

    abstract fun run(a:A, b:B, c:C, d:D)

    override fun handle(){
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any) {
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any, b: Any) {
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any, b: Any, c: Any) {
        throw  NotImplementedError("Not yet implemented")
    }

    override fun handle(a: Any, b: Any, c: Any, d: Any) {
        this.run(a as A, b as B,c as C, d as D)
    }
}