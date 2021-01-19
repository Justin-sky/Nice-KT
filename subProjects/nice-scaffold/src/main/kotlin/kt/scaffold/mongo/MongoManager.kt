package kt.scaffold.mongo

import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import kt.scaffold.Application
import kt.scaffold.tools.KtException
import kt.scaffold.tools.json.toShortJson

object MongoManager {

    private val instances = mutableMapOf<String, MongoClient>()

    fun mongoOf(name:String):MongoClient{
        return instances[name]?:createMongo(name)
    }

    private fun createMongo(name:String,shared:Boolean = true):MongoClient{

        return instances.getOrPut(name){
            var client: MongoClient?
            if(shared){
                client = MongoClient.createShared(
                    Application.vertx,
                    optionsOf(name)
                )
            }else{
                client = MongoClient.create(
                    Application.vertx,
                    optionsOf(name)
                )
            }
            return client
        }
    }

    private fun optionsOf(name:String):JsonObject{
        var cfgPath = "mongo.$name"
        if (Application.config.hasPath(cfgPath)){
            return JsonObject(Application.config.getConfig(cfgPath).root().unwrapped().toShortJson())
        }else{
            throw KtException("Please check application.conf, the name of mongo: [$name] does not exists.")
        }
    }

}