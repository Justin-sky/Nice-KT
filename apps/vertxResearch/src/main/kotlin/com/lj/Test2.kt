package com.lj

import io.vertx.core.Vertx
import io.vertx.core.VertxOptions

fun main(args:Array<String>){
    println(Thread.currentThread())
    val vertx = Vertx.vertx(VertxOptions().setEventLoopPoolSize(10))
    for (i in 0 .. 20){
        val index = i
        vertx.setTimer(1){ timeID->
            println("$index : ${Thread.currentThread()}")
        }
    }
}