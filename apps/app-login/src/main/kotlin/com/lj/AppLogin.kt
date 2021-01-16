package com.lj

import com.lj.core.net.SocketManager
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

    //心跳检查
    Application.vertx.setPeriodic(1000*30){
        SocketManager.checkTimeout(1000*60)
    }

}