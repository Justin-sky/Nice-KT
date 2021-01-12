package com.lj

import kt.scaffold.Application

suspend fun main() {

    //初始化Vertx
    Application.setupVertx()

    //启动Game Server
    Application.deployVerticle(GameServerServiceVerticle::class.java.name,"GameServerVerticle")



}