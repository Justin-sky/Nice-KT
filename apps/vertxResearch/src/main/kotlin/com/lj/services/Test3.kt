package com.lj.services

import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx

fun main() {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(object :AbstractVerticle(){
        override fun start() {
           vertx.eventBus().consumer<String>("the-address"){ msg->
               try {
                   Thread.sleep(10)
                   System.out.println("Executed by ${Thread.currentThread()}")
                   msg.reply("whatever")
               }catch (e:InterruptedException){
                   msg.fail(0,"Interrupted")
               }
           }
        }
    }, DeploymentOptions().setWorker(true))
    send(vertx,10)
}

fun send(vertx:Vertx, count:Int){
    if (count>0){
        vertx.eventBus().request<String>("the-address", count){ reply->
            send(vertx, count -1)
        }
    }else{
        System.exit(0)
    }
}