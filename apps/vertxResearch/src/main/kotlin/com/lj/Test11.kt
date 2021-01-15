package com.lj

import io.vertx.core.Vertx

fun main() {
    val vertx = Vertx.vertx()
    val context = vertx.orCreateContext
    println("Current context is ${Vertx.currentContext()}")
    println("context $context")

    context.runOnContext(){v->
        println("current context is ${Vertx.currentContext()}")
    }
}