package kt.scaffold.common

import io.vertx.circuitbreaker.CircuitBreaker
import io.vertx.circuitbreaker.CircuitBreakerOptions
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kt.scaffold.Application
import kt.scaffold.net.DiscoveryManager


open class MicroServiceVerticle :CoroutineVerticle(){

    lateinit var  circuitBreaker: CircuitBreaker

    override suspend fun start() {
        //init circuit breaker instance
        val cbOptions = Application.circuitBreakerOptions()

        circuitBreaker = CircuitBreaker.create(cbOptions.getString("name", "circuit-breaker"),
            vertx,
            CircuitBreakerOptions()
                .setMaxFailures(cbOptions.getInteger("maxFailures", 5))
                .setTimeout(cbOptions.getLong("timeout", 10000L))
                .setFallbackOnFailure(cbOptions.getBoolean("fallbackOnFailure",true))
                .setResetTimeout(cbOptions.getLong("resetTimeout", 30000L)
            )
        )
    }


    override suspend fun stop() {
        DiscoveryManager.unPublishAll()
        DiscoveryManager.close()
    }
}