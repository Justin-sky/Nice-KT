package com.lj.services

import io.vertx.core.Vertx

fun main() {
    val vertx = Vertx.vertx()
    println("Current context is ${Vertx.currentContext()}")
}