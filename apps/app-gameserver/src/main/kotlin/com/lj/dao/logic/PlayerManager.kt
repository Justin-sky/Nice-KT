package com.lj.dao.logic

import io.vertx.core.json.JsonObject
import kt.scaffold.mongo.MongoManager
import kt.scaffold.tools.logger.Logger

object PlayerManager {
    val userDB = "niceMongo"
    val tableName = "user"

    fun getPlayerInfo(playerId:Int){

        val mongo = MongoManager.mongoOf(userDB)

        var query = JsonObject()
        query.put("_id", playerId)

        val playerInfo = mongo.find(tableName, query){res->
            if (res.succeeded()){
                Logger.debug("succ....")
            }else{
                Logger.debug("fail.....")
            }
        }
    }


}