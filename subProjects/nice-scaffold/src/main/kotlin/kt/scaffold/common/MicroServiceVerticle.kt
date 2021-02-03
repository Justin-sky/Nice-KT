package kt.scaffold.common

import io.vertx.circuitbreaker.CircuitBreaker
import io.vertx.circuitbreaker.CircuitBreakerOptions
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kt.scaffold.Application
import kt.scaffold.net.DiscoveryManager


open class MicroServiceVerticle :CoroutineVerticle(){

    override suspend fun start() {

    }

    override suspend fun stop() {
        DiscoveryManager.unPublishAll()
        DiscoveryManager.close()
    }
}