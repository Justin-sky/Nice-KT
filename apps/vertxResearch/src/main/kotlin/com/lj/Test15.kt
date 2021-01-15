package com.lj

import io.vertx.core.*
import java.lang.RuntimeException

fun main() {
    val vertx = Vertx.vertx()
    vertx.runOnContext(){v->
        println("Calling blocking block from ${Thread.currentThread()}")

        val blockingCodeHandler =
            Handler { future: Promise<String?> ->
                try{
                    throw RuntimeException()
                }catch (e:Exception){
                    future.fail(e)
                }

            }

        val resultHandler =
            Handler { result: AsyncResult<String?>? ->
                if (result?.succeeded() == true){
                    print("Got result")
                }else{
                    print("Blocking code failed")
                    if (result != null) {
                        result.cause().printStackTrace()
                    }
                }
            }

        vertx.executeBlocking(blockingCodeHandler,resultHandler);
    }
}