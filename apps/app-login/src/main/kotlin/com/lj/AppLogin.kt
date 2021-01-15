package com.lj

import com.lj.core.net.msg.MessageDispatcher
import kt.scaffold.Application

suspend fun main() {

    //注册消息处理器
    MessageDispatcher.initialize("com.lj.msg")

    //初始化Vertx
    Application.setupVertx()

    //启动Login Server
    Application.deployVerticle(LoginVerticle::class.java.name,"LoginVerticle")

    //设置回调
    Application.setupOnStartAndOnStop()


}