package com.lj

import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions

fun main() {
    val vertx = Vertx.vertx(VertxOptions().setWorkerPoolSize(2))
    vertx.deployVerticle(
        TheWorker::class.java.name,
                DeploymentOptions().setWorker(true).setInstances(2)
    )
    for(i in 0..10){
        vertx.eventBus().request<String>("the-address","the-message"){ replay->
            println(replay.result().body())
        }
    }
}