package com.lj.services

import kt.scaffold.Application
import kt.scaffold.tools.logger.Logger

fun main(args:Array<String>){

    //初始化Vertx
    Application.setupVertx()

    //启动Gateway Server
    Application.deployVerticle(GatewayVerticle::class.java.name,"GatewayVerticle")

    //设置回调
    Application.setupOnStartAndOnStop()
}