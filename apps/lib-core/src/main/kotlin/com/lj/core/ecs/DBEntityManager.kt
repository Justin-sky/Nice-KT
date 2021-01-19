package com.lj.core.ecs

import com.lj.core.consts.GlobalConst
import com.lj.core.ecs.entity.PlayerEntity
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import io.vertx.kotlin.ext.mongo.findOneAwait
import io.vertx.kotlin.ext.mongo.saveAwait
import io.vertx.kotlin.ext.mongo.updateCollectionAwait
import kt.scaffold.mongo.MongoManager
import kt.scaffold.tools.logger.Logger
import java.lang.Exception

object DBEntityManager {

    val gameServerMongo = MongoManager.mongoOf(GlobalConst.GAMESERVER_MONGO)
    /**
     * 保存Entity到DB
     */
    suspend fun saveEntity2DBAwait(entity: Entity) {
        val json = JsonObject.mapFrom(entity)

        gameServerMongo.saveAwait(entity.docName,json)

    }

    suspend fun findEntityAwait(uid:Long): Entity? {
       val json = gameServerMongo.findOneAwait("user", JsonObject().put("_id", uid), JsonObject())
        if(json!=null){
            val entity =  json.mapTo(PlayerEntity::class.java)
            entity.components.values?.forEach(){component->
                component.entity = entity
            }
            return entity
        }
        return null
    }

    /**
     * 更新Entity中的Component到DB
     */
    suspend fun updateEntity2DBAwait(entity: Entity){

        entity.components.values.forEach(){ component->
            val pullJson = component.getPullJson()
            val pushJson = component.getPushJson()
            val updateJson = component.getUpdateJson()
            val unsetJson = component.getUnsetJson()

            val q = component.query

            if(!updateJson.isEmpty) gameServerMongo.updateCollectionAwait(entity.docName, q, JsonObject().put("\$set",updateJson))
            if(!unsetJson.isEmpty) gameServerMongo.updateCollectionAwait(entity.docName, q, JsonObject().put("\$unset",unsetJson))
            if(!pushJson.isEmpty) gameServerMongo.updateCollectionAwait(entity.docName, q, JsonObject().put("\$push",pushJson))
            if(!pullJson.isEmpty) gameServerMongo.updateCollectionAwait(entity.docName, q, JsonObject().put("\$pull",pullJson))

        }

    }

    suspend fun updateComponent2DBAwait(entity: Entity){

        val q = JsonObject().put("_id",entity._id)
        val componentJson = entity.updateComponentJson

        if(!componentJson.isEmpty) {
            gameServerMongo.updateCollectionAwait(entity.docName, q, JsonObject().put("\$set", componentJson))
            componentJson.clear()
        }
    }

    suspend fun removeComponent2DBAwait(entity: Entity){

        val q = JsonObject().put("_id",entity._id)
        val rmcomponentJson = entity.removeComponentJson
        if(!rmcomponentJson.isEmpty) gameServerMongo.updateCollectionAwait(entity.docName, q, JsonObject().put("\$unset",rmcomponentJson))
        rmcomponentJson.clear()
    }

}