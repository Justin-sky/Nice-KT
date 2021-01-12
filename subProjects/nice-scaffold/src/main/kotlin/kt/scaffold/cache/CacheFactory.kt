package kt.scaffold.cache

import io.vertx.core.json.JsonObject

interface CacheFactory {

    fun createCache(options: JsonObject): CacheApi

    fun createAsyncCache(options: JsonObject): AsyncCacheApi

}