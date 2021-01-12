package com.lj.services

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx

fun main() {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(object:AbstractVerticle(){
        override fun start() {
            println("Current context is ${Vertx.currentContext()}")
            println("Verticle context is $context")
            System.exit(0)
        }
    })
}