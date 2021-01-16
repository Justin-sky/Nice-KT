package com.lj

import com.lj.core.eventBus.EventBusAddress
import com.lj.core.service.GameService
import com.lj.core.service.impl.GameServiceImpl
import com.lj.dao.pojo.BagItemPojo
import com.lj.dao.pojo.BagPojo
import com.lj.dao.pojo.PlayerPojo
import io.vertx.core.json.JsonObject
import io.vertx.serviceproxy.ServiceBinder
import kt.scaffold.Application
import kt.scaffold.common.MicroServiceVerticle
import kt.scaffold.mongo.MongoManager
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


        //====================
        var bagItem = BagItemPojo()
        bagItem.id = 2
        bagItem.name = "item"

        var bagItems = listOf<BagItemPojo>(bagItem)

        var bag = BagPojo()
        bag.id = 1
        bag.size =1
        bag.items = bagItems

        val obj = PlayerPojo()
        obj.id = 1;
        obj.name = "justin"
        obj.bag = bag
        val json = JsonObject.mapFrom(obj)

        var obj2 = json.mapTo(PlayerPojo::class.java)

//        val mongo = MongoManager.mongoOf("niceMongo")
//
//        var query = JsonObject()
//        var updatet = JsonObject()
//        mongo.updateCollection("90001",query, updatet){rs->
//
//        }

        Logger.debug(json.toString())
    }

}