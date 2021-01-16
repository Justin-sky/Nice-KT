package com.lj

import com.lj.core.net.msg.MsgMessageCodec
import com.lj.core.service.Msg
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

    //注册eventbus编码器
    val eventBus = Application.vertx.eventBus()
    eventBus.registerDefaultCodec(Msg::class.java, MsgMessageCodec())

}