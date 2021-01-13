package com.lj.services

import com.lj.GameServerServiceVerticle
import com.lj.core.net.msg.MessageDispatcher
import kt.scaffold.Application

suspend fun main() {

    //注册消息处理器
    MessageDispatcher.initialize("com.lj.services.msg")

    //初始化Vertx
    Application.setupVertx()

    //启动Login Server
    Application.deployVerticle(LoginVerticle::class.java.name,"LoginVerticle")


    //启动 Gateway Server
    Application.deployVerticle(GatewayVerticle::class.java.name,"GatewayVerticle")

    //启动Game Server
    Application.deployVerticle(GameServerServiceVerticle::class.java.name,"GameServerVerticle")


    //设置回调
    Application.setupOnStartAndOnStop()




}