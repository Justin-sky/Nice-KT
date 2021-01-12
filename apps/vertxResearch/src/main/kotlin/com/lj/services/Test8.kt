package com.lj.services

import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import java.util.concurrent.atomic.AtomicLong

fun main() {
    val vertx = Vertx.vertx(VertxOptions().setEventLoopPoolSize(10))
    vertx.deployVerticle(object :AbstractVerticle(){
        override fun start() {
            val count = AtomicLong(10)
            val now = System.currentTimeMillis()
            println("Starting periodic on ${Thread.currentThread()}")
            vertx.setPeriodic(1000){id->
                if (count.decrementAndGet() < 0){
                    vertx.cancelTimer(id)
                    System.exit(0)
                }
                println("Periodic fired ${Thread.currentThread()} after ${System.currentTimeMillis()-now} ms")
            }
        }
    }, DeploymentOptions().setWorker(true))

}