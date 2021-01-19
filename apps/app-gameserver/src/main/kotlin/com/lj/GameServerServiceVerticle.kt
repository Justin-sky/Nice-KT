package com.lj

import com.lj.core.consts.EventBusAddress
import com.lj.core.ecs.DBEntityManager
import com.lj.core.ecs.component.AccountComponent
import com.lj.core.ecs.component.BagComponent
import com.lj.core.ecs.entity.PlayerEntity
import com.lj.core.service.GameService
import com.lj.core.service.impl.GameServiceImpl
import io.vertx.serviceproxy.ServiceBinder
import kt.scaffold.Application
import kt.scaffold.common.MicroServiceVerticle
import kt.scaffold.net.DiscoveryManager
import kt.scaffold.tools.logger.Logger

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
        DiscoveryManager.publishEventBusServiceAwait(
            EventBusAddress.SERVICE_GAMESERVER_NAME,
            EventBusAddress.SERVICE_GAMESERVER_ADDRESS,
            GameService::class.java,
            serverID.toInt(),
            serverType.toInt()
        )
    }
}