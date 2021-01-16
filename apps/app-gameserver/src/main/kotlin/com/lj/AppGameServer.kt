package com.lj

import fb.FBManager
import com.lj.core.service.handler.ServiceDispatcher
import kt.scaffold.Application

suspend fun main() {

    //Service消息处理
    ServiceDispatcher.initialize("com.lj.msg")

    //加载静态表数据
    FBManager.initialize()

    //初始化Vertx
    Application.setupVertx()

    //启动Game Server
    Application.deployVerticle(GameServerServiceVerticle::class.java.name,"GameServerVerticle")



}