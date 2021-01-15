package com.lj

import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import java.util.concurrent.atomic.AtomicLong

fun main() {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(object:AbstractVerticle(){
        override fun start() {
            val count = AtomicLong(10)
            val now = System.currentTimeMillis()
            println("Starting periodic on ${Thread.currentThread()}")
            vertx.setPeriodic(1000){id->
                run {
                    if (count.decrementAndGet() < 0) {
                        vertx.cancelTimer(id)
                    }
                    println("Periodic fired ${Thread.currentThread()} after ${System.currentTimeMillis() - now}")
                }
            }
        }
    }, DeploymentOptions().setWorker(true))
}