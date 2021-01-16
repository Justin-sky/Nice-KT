package com.lj

import com.lj.core.eventBus.EventBusAddress
import com.lj.core.net.SocketManager
import com.lj.core.net.msg.MsgMessageCodec
import com.lj.core.service.Msg
import kt.scaffold.Application

suspend fun main(args:Array<String>){

    //初始化Vertx
    Application.setupVertx()

    //启动Gateway Server
    Application.deployVerticle(GatewayVerticle::class.java.name,"GatewayVerticle")

    //设置回调
    Application.setupOnStartAndOnStop()

    //心跳检查
    Application.vertx.setPeriodic(1000*30){
        SocketManager.checkTimeout(1000*60)
    }

    //网关服监听推送消息
    val eventBus = Application.vertx.eventBus()
    eventBus.registerDefaultCodec(Msg::class.java, MsgMessageCodec())

    val consumer = eventBus.consumer<Msg>(EventBusAddress.PUSH2CLIENT_ADDRESS)
    consumer.handler { message ->
        val msg = message.body()
        SocketManager.pushMsg2Client(msg.userId, msg)
    }


}