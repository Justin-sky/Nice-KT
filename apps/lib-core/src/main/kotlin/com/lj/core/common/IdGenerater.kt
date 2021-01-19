package com.lj.core.common

import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import io.vertx.kotlin.ext.mongo.findOneAndUpdateAwait
import kt.scaffold.mongo.MongoManager
import kt.scaffold.tools.logger.Logger

object IdGenerater {
    lateinit var mongo: MongoClient

    fun initUUID(mongoName:String){
        mongo = MongoManager.mongoOf(mongoName)

        val q = JsonObject().put("type","uid")
        mongo.find("UUID",q){res->
            if (res.succeeded()){
                if (res.result().isNullOrEmpty()){
                    val uuid = JsonObject()
                    uuid.put("type", "uid")
                    uuid.put("_id", 1)
                    mongo.createIndex("UUID",JsonObject().put("type",1)){

                    }
                    mongo.save("UUID", uuid){res->
                        if (res.succeeded()){
                            Logger.debug("init uuid success")
                        }else{
                            Logger.debug("init uuid failed")
                        }
                    }
                }

            }
        }
    }


    suspend  fun generatedUidAwait():Long?{
        val q = JsonObject().put("type","uid")

        val m = JsonObject()
        m.put("\$inc",JsonObject().put("uid",1))

        val res = mongo.findOneAndUpdateAwait("UUID",q, m)

        return res?.getLong("_id")
    }
}