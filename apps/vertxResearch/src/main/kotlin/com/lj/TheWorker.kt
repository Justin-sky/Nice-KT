package com.lj

import io.vertx.core.AbstractVerticle
import java.util.concurrent.atomic.AtomicInteger

class TheWorker: AbstractVerticle() {

    companion object{
        val serial = AtomicInteger()
    }

    val id = serial.incrementAndGet()

    override fun start() {
        vertx.eventBus().consumer<String>("the-address"){msg ->
            try {
                Thread.sleep(10)
                msg.reply("Executed by worker $id with ${Thread.currentThread()}")
            }catch (e:InterruptedException){
                msg.fail(0,"Interrupted")
            }
        }
    }
}