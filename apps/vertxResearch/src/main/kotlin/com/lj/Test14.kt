package com.lj

import io.vertx.core.*

fun main() {
    val vertx = Vertx.vertx()
    vertx.runOnContext(){v->
        println("Calling blocking block from ${Thread.currentThread()}")

        val blockingCodeHandler =
            Handler { future: Promise<String?> ->
                // Non event loop
                println("Computing with " + Thread.currentThread())
                future.complete("some result")
            }

        val resultHandler =
            Handler { result: AsyncResult<String?>? ->
                // Back to the event loop
                println("Got result in " + Thread.currentThread())
            }

        vertx.executeBlocking(blockingCodeHandler,resultHandler);
    }
}