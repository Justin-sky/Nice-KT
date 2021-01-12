package com.lj.services

import io.vertx.core.Vertx

fun main(args:Array<String>){

    val vertx = Vertx.vertx()
    vertx.setTimer(1) { id ->
        run {
            //阻塞Vert.x事件循环
            try {
                Thread.sleep(7000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}