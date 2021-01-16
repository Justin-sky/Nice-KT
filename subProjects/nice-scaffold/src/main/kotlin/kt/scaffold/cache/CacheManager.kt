package kt.scaffold.cache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.CacheLoader
import com.github.benmanes.caffeine.cache.Caffeine
import com.google.common.graph.Graph
import io.vertx.redis.client.Redis
import io.vertx.redis.client.RedisOptions
import kt.scaffold.Application
import kt.scaffold.redis.RedisManager
import java.util.concurrent.TimeUnit

/**
 * 基于Caffeine 缓存管理
 */
object CacheManager {


    fun <Key,Graph> createCache(name: Key):Cache<Key,Graph>{

        val cache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(10,TimeUnit.SECONDS)
            .build<Key, Graph>();
        return cache
    }




}