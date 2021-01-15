package com.lj

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx

fun main() {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(object :AbstractVerticle(){
        override fun start() {
            val context = vertx.orCreateContext
            println(context)
            println(vertx.orCreateContext)
        }
    })
}