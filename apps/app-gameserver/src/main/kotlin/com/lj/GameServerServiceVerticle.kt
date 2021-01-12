package com.lj

import com.lj.core.eventBus.EventBusAddress
import com.lj.services.GameServerService
import com.lj.services.impl.GameServerServiceImpl
import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import io.vertx.serviceproxy.ServiceBinder
import kt.scaffold.common.MicroServiceVerticle

class GameServerServiceVerticle : MicroServiceVerticle() {
    override suspend fun start() {
        super.start()

        val service = GameServerServiceImpl()
        val binder = ServiceBinder(vertx)
        binder.setAddress(EventBusAddress.SERVICE_GAMESERVER_ADDRESS).register(GameServerService::class.java,service)
        //binder.setAddress("com.lj.server").registerLocal(GameServerService::class.java,service)

        val meta = JsonObject()
        meta.put("server_id",1000)
        publishEventBusService(
            EventBusAddress.SERVICE_GAMESERVER_NAME,
            EventBusAddress.SERVICE_GAMESERVER_ADDRESS,
            GameServerService::class.java,
            meta)
    }

}