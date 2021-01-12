package kt.scaffold.cache

import io.vertx.core.json.JsonObject
import kt.scaffold.Application
import kt.scaffold.tools.KtException
import kt.scaffold.tools.json.toShortJson


object CacheManager {

    private val cacheApiMap = mutableMapOf<String, CacheApi>()

    private val asyncCacheApiMap = mutableMapOf<String, AsyncCacheApi>()

    /**
     * get cache instence by cache name.
     */
    fun cache(cacheName: String): CacheApi {
        return cacheApiMap[cacheName] ?: createCache(cacheName)
    }

    /**
     * get async cache instence by cache name.
     */
    fun asyncCache(cacheName: String): AsyncCacheApi {
        return asyncCacheApiMap[cacheName] ?: createAsyncCache(cacheName)
    }

    @Synchronized
    private fun createCache(cacheName: String): CacheApi {
        return cacheApiMap.getOrPut(cacheName) {
            val options = cacheOptionsOf(cacheName)
            factoryOf(cacheName).createCache(options)
        }
    }

    @Synchronized
    private fun createAsyncCache(cacheName: String): AsyncCacheApi {
        return asyncCacheApiMap.getOrPut(cacheName) {
            val options = cacheOptionsOf(cacheName)
            factoryOf(cacheName).createAsyncCache(options)
        }
    }

    private fun factoryOf(cacheName: String): CacheFactory {
        checkCacheName(cacheName)

        val cfgPath = "app.cache.configs.${cacheName}.factory"
        if (Application.config.hasPath(cfgPath).not()) {
            throw KtException("Please check application.conf to make sure that the \"factory\" class of the specified cache [$cacheName] is set correctly.")
        }
        val factoryClassName = Application.config.getString(cfgPath)
        val factoryClass = Application.classLoader.loadClass(factoryClassName)
        return factoryClass.getDeclaredConstructor().newInstance() as CacheFactory
    }

    private fun cacheOptionsOf(cacheName: String): JsonObject {
        checkCacheName(cacheName)

        val cfgPath = "app.cache.configs.${cacheName}.options"
        if (Application.config.hasPath(cfgPath).not()) {
            throw KtException("Please check application.conf to make sure that the options of the specified cache [$cacheName] is set correctly.")
        }
        val optionsJson = Application.config.getConfig(cfgPath).root().unwrapped().toShortJson()
        return JsonObject(optionsJson)
    }

    private fun checkCacheName(cacheName: String) {
        val cfgPath = "app.cache.configs.${cacheName}"
        if (Application.config.hasPath(cfgPath).not()) {
            throw KtException("Please check application.conf, the name of cache: [$cacheName] does not exists.")
        }
    }
}