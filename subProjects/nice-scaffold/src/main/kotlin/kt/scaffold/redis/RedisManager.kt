package kt.scaffold.redis

import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.file.openOptionsOf
import io.vertx.redis.client.Redis
import io.vertx.redis.client.RedisOptions
import kt.scaffold.Application
import kt.scaffold.tools.KtException
import kt.scaffold.tools.json.toShortJson

object RedisManager {

    private val instances = mutableMapOf<String, Redis>()

    fun redisOf(name: String):Redis{
        return instances[name]?:createRedis(name)
    }

    @Synchronized
    private fun createRedis(name: String):Redis{
        return instances.getOrPut(name){
            Redis.createClient(
                Application.vertx,
                RedisOptions(optionsOf(name)))
        }
    }

    private fun optionsOf(name: String):JsonObject{
        var cfgPath = "redis.$name"
        if (Application.config.hasPath(cfgPath)){
            return JsonObject(Application.config.getConfig(cfgPath).root().unwrapped().toShortJson())
        }else{
            throw KtException("Please check application.conf, the name of redis: [$name] does not exists.")
        }
    }

}