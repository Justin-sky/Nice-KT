package com.lj.services

import com.lj.GameServerServiceVerticle
import kt.scaffold.Application

fun main() {

    //初始化Vertx
    Application.setupVertx()

    //启动 Gateway Server
    Application.deployVerticle(GatewayVerticle::class.java.name,"GatewayVerticle")

    //启动Game Server
    Application.deployVerticle(GameServerServiceVerticle::class.java.name,"GameServerVerticle")


    //设置回调
    Application.setupOnStartAndOnStop()




}