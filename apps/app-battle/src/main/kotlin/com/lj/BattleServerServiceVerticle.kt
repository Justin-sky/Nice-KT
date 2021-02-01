package com.lj

import com.lj.core.consts.EventBusAddress
import com.lj.core.service.GameService
import com.lj.core.service.impl.GameServiceImpl
import io.vertx.serviceproxy.ServiceBinder
import kt.scaffold.Application
import kt.scaffold.common.MicroServiceVerticle
import kt.scaffold.net.DiscoveryManager

class BattleServerServiceVerticle:MicroServiceVerticle() {

    private val battles = mutableMapOf<Long, GamePlay>()

    override suspend fun start() {
        super.start()

        val serverID = Application.config.getString("app.Verticles.BattleServerVerticle.serverID")
        val serverType = Application.config.getInt("app.Verticles.BattleServerVerticle.serverType")
        val binder = ServiceBinder(vertx)
        //==================此处注册并发布服务
        binder.setAddress(EventBusAddress.SERVICE_BATTLESERVER_ADDRESS).register(
            GameService::class.java,
            GameServiceImpl()
        )
        DiscoveryManager.publishEventBusServiceAwait(
            EventBusAddress.SERVICE_BATTLESERVER_NAME,
            EventBusAddress.SERVICE_BATTLESERVER_ADDRESS,
            GameService::class.java,
            serverID.toInt(),
            serverType.toInt()
        )

    }
}