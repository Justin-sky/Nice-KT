package kt.scaffold.cache.local

import com.google.common.cache.CacheBuilder
import io.vertx.core.json.JsonObject
import kt.scaffold.cache.AsyncCacheApi
import kt.scaffold.cache.CacheApi
import kt.scaffold.cache.CacheFactory

class LocalCacheFactory : CacheFactory {
    override fun createCache(options: JsonObject): CacheApi {
        val maximumSize = options.getLong("maximumSize") ?: 2048
        return LocalCache(CacheBuilder.newBuilder().maximumSize(maximumSize).build())
    }

    override fun createAsyncCache(options: JsonObject): AsyncCacheApi {
        val maximumSize = options.getLong("maximumSize") ?: 2048
        return LocalAsyncCache(CacheBuilder.newBuilder().maximumSize(maximumSize).build())
    }
}