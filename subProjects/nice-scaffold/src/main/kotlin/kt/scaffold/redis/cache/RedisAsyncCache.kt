package kt.scaffold.redis.cache

import io.vertx.kotlin.redis.client.delAwait
import io.vertx.kotlin.redis.client.existsAwait
import io.vertx.kotlin.redis.client.getAwait
import io.vertx.redis.client.Redis
import kt.scaffold.cache.AsyncCacheApi
import kt.scaffold.redis.api
import kt.scaffold.redis.psetexAwait
import kt.scaffold.redis.setAwait
import kt.scaffold.tools.KtException
import kt.scaffold.tools.logger.Logger


class RedisAsyncCache(private val redis: Redis) : AsyncCacheApi {

    override suspend fun existsAwait(key: String): Boolean {
        return try {
            redis.api().existsAwait(listOf(key))!!.toInteger() == 1
        } catch (e: Throwable) {
            false
        }
    }

    override suspend fun delAwait(key: String) {
        try {
            redis.api().delAwait(listOf(key))
        } catch (e: Throwable) {
            Logger.warn(e.localizedMessage)
        }
    }

    override suspend fun getBytesAwait(key: String): ByteArray {
        return redis.api().getAwait(key)?.toBytes() ?: throw KtException("'$key' does not exist in the cache.")
    }

    override suspend fun getBytesOrElseAwait(key: String, default: () -> ByteArray): ByteArray {
        return try {
            redis.api().getAwait(key)?.toBytes() ?: default()
        } catch (e: Throwable) {
            Logger.warn(e.localizedMessage)
            default()
        }
    }

    override suspend fun getBytesOrNullAwait(key: String): ByteArray? {
        return try {
            redis.api().getAwait(key)?.toBytes()
        } catch (e: Throwable) {
            Logger.warn(e.localizedMessage)
            null
        }
    }

    override suspend fun setBytesAwait(key: String, value: ByteArray) {
        try {
            redis.setAwait(key, value)
        } catch (e: Throwable) {
            Logger.warn(e.localizedMessage)
        }
    }

    override suspend fun setBytesAwait(key: String, value: ByteArray, expirationInMs: Long) {
        try {
            if (expirationInMs > 0) {
                redis.psetexAwait(key, value, expirationInMs)
            } else {
                redis.setAwait(key, value)
            }
        } catch (e: Throwable) {
            Logger.warn(e.localizedMessage)
        }
    }


}