package com.lj.services

import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx

fun main() {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(
        TheWorker::class.java.name,
        DeploymentOptions().setWorker(true).setInstances(3)
    )
    for (i in 0..10){
        vertx.eventBus().request<String>("the-address","the-message"){ reply->
            println(reply.result().body())
        }
    }
}