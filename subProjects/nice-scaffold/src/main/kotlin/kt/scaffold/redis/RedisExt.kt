package kt.scaffold.redis

import io.vertx.kotlin.redis.client.sendAwait
import io.vertx.redis.client.*

suspend fun Redis.setAwait(key: String, value: ByteArray): Response? {
    val req = Request.cmd(Command.SET).arg(key).arg(value)
    return this.sendAwait(req)
}

suspend fun Redis.psetexAwait(key: String, value: ByteArray, expirationInMs: Long): Response? {
    val req = Request.cmd(Command.PSETEX).arg(key).arg(expirationInMs).arg(value)
    return this.sendAwait(req)
}

fun Redis.api(): RedisAPI {
    return RedisAPI.api(this)
}