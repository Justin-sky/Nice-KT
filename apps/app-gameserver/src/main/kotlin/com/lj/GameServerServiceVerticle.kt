package com.lj

import com.lj.core.eventBus.EventBusAddress
import com.lj.core.service.GameService
import com.lj.core.service.impl.GameServiceImpl
import io.vertx.serviceproxy.ServiceBinder
import kt.scaffold.Application
import kt.scaffold.common.MicroServiceVerticle
import kt.scaffold.net.DiscoveryManager

class GameServerServiceVerticle : MicroServiceVerticle() {
    override suspend fun start() {
        super.start()

        val serverID = Application.config.getString("app.Verticles.GameServerVerticle.serverID")
        val serverType = Application.config.getInt("app.Verticles.GameServerVerticle.serverType")
        val binder = ServiceBinder(vertx)
        //==================此处注册并发布服务
        binder.setAddress(EventBusAddress.SERVICE_GAMESERVER_ADDRESS).register(GameService::class.java,
            GameServiceImpl()
        )
        DiscoveryManager.publishEventBusService(
            EventBusAddress.SERVICE_GAMESERVER_NAME,
            EventBusAddress.SERVICE_GAMESERVER_ADDRESS,
            GameService::class.java,
            serverID.toInt(),
            serverType.toInt()
        )


        //====================



    }

}