package com.lj

import com.lj.core.eventBus.EventBusAddress
import com.lj.services.BenchmarkService
import com.lj.services.impl.BenchmarkServiceImpl
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
        binder.setAddress(EventBusAddress.SERVICE_GAMESERVER_ADDRESS).register(BenchmarkService::class.java, BenchmarkServiceImpl())
        DiscoveryManager.publishEventBusService(
            EventBusAddress.SERVICE_GAMESERVER_NAME,
            EventBusAddress.SERVICE_GAMESERVER_ADDRESS,
            BenchmarkService::class.java,
            serverID.toInt(),
            serverType.toInt()
        )


        //====================



    }

}