package com.lj

import com.lj.core.ecs.Entity
import com.lj.core.gamePlay.combat.CombatContext
import com.lj.core.gamePlay.combat.entity.logic.LogicEntity
import com.lj.core.net.msg.MsgMessageCodec
import com.lj.core.service.Msg
import fb.FBManager
import kt.scaffold.Application
import kt.scaffold.tools.logger.Logger
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis


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