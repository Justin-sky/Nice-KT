package com.lj.services

import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx

fun main() {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(object:AbstractVerticle(){
        override fun start() {
            val now = System.currentTimeMillis()
            println("Starting timer on ${Thread.currentThread()}")
            vertx.setTimer(1000){id->
                run {
                    println("Timer fired ${Thread.currentThread()} after ${System.currentTimeMillis()-now} ms")
                }
            }
        }
    }, DeploymentOptions().setWorker(true))

}