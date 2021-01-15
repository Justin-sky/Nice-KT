package com.lj

import com.lj.GameServerServiceVerticle
import com.lj.core.net.msg.MessageDispatcher
import com.lj.core.service.handler.ServiceDispatcher
import kt.scaffold.Application

suspend fun main() {

    //注册消息处理器
    MessageDispatcher.initialize("com.lj.msg")
    //Service消息处理
    ServiceDispatcher.initialize("com.lj.msg")

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