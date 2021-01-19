package com.lj

import com.lj.core.common.IdGenerater
import com.lj.core.consts.EventBusAddress
import com.lj.core.net.SocketManager
import fb.FBManager
import com.lj.core.net.msg.MessageDispatcher
import com.lj.core.net.msg.MsgMessageCodec
import com.lj.core.service.Msg
import com.lj.core.service.handler.ServiceDispatcher
import kt.scaffold.Application

suspend fun main() {

    //注册消息处理器
    MessageDispatcher.initialize("com.lj.msg")
    //Service消息处理
    ServiceDispatcher.initialize("com.lj.msg")


    //加载静态表数据
    FBManager.initialize()

    //初始化Vertx
    Application.setupVertx()

    //初始化UUID
    IdGenerater.initUUID("niceMongo")

    //启动Login Server
    Application.deployVerticle(LoginVerticle::class.java.name,"LoginVerticle")


    //启动 Gateway Server
    Application.deployVerticle(GatewayVerticle::class.java.name,"GatewayVerticle")

    //启动Game Server
    Application.deployVerticle(GameServerServiceVerticle::class.java.name,"GameServerVerticle")

    //设置回调
    Application.setupOnStartAndOnStop()

    //注册eventbus编码器
    val eventBus = Application.vertx.eventBus()
    eventBus.registerDefaultCodec(Msg::class.java, MsgMessageCodec())

    val consumer = eventBus.consumer<Msg>(EventBusAddress.PUSH2CLIENT_ADDRESS)
    consumer.handler { message ->
        val msg = message.body()
        SocketManager.pushMsg2Client(msg.userId, msg)
    }
}