package com.lj

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx

fun main() {
    val vertx = Vertx.vertx()
    var numberOfFiles:Int;

    vertx.deployVerticle(object :AbstractVerticle(){
        override fun start() {

            val context = Vertx.currentContext()
            println("Running with context ${Vertx.currentContext()}")

            object :Thread() {
                 override fun run(){
                    println("Current context : ${Vertx.currentContext()}")

                    var n = getNumberOfFiles()
                    context.runOnContext(){v->
                        println("Runs on the orginal context : ${Vertx.currentContext()}")
                        numberOfFiles = n
                    }
                }
            }.start()
        }
    })
}

fun getNumberOfFiles(): Int {
    return 10
}