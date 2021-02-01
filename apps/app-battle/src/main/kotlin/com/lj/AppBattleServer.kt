package com.lj

import com.lj.core.net.msg.MsgMessageCodec
import com.lj.core.service.Msg
import fb.FBManager
import kt.scaffold.Application


suspend fun main() {

    //加载静态表数据
    FBManager.initialize()

    //初始化Vertx
    Application.setupVertx()


    //启动Game Server
    Application.deployVerticle(BattleServerServiceVerticle::class.java.name,"BattleServerVerticle")

    //注册eventbus编码器
    val eventBus = Application.vertx.eventBus()
    eventBus.registerDefaultCodec(Msg::class.java, MsgMessageCodec())

}