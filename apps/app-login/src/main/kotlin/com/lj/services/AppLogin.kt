package com.lj.services

import com.lj.core.net.msg.MessageDispatcher
import com.lj.core.net.msg.Msg
import com.lj.proto.OpcodeOuterClass
import kt.scaffold.Application

suspend fun main() {

    //初始化Vertx
    Application.setupVertx()

    //启动Login Server
    Application.deployVerticle(LoginVerticle::class.java.name,"LoginVerticle")

    //设置回调
    Application.setupOnStartAndOnStop()


}