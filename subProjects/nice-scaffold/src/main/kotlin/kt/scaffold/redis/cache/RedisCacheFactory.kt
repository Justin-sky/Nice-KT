package kt.scaffold.redis.cache

import io.vertx.core.json.JsonObject
import io.vertx.redis.client.Redis
import io.vertx.redis.client.RedisOptions
import kt.scaffold.Application
import kt.scaffold.cache.AsyncCacheApi
import kt.scaffold.cache.CacheApi
import kt.scaffold.cache.CacheFactory


class RedisCacheFactory: CacheFactory {
    override fun createCache(options: JsonObject): CacheApi {
        val redis = Redis.createClient(Application.vertx, RedisOptions(options))
        return RedisCache(redis)
    }

    override fun createAsyncCache(options: JsonObject): AsyncCacheApi {
        val redis = Redis.createClient(Application.vertx, RedisOptions(options))
        return RedisAsyncCache(redis)
    }
}