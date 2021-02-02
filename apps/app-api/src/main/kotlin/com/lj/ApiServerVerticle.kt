package com.lj

import io.vertx.core.AbstractVerticle
import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServerOptions
import io.vertx.ext.dropwizard.MetricsService
import io.vertx.ext.web.Router
import kt.scaffold.Application
import kt.scaffold.tools.logger.Logger

class ApiServerVerticle : AbstractVerticle() {
    override  fun start() {
        super.start()

        val port =Application.config.getInt("app.httpServer.port")
        val host =Application.config.getString("app.httpServer.host")

        val options = HttpServerOptions()
        options.host = host
        options.port = port

        val server = vertx.createHttpServer(options)

        val router = Router.router(vertx)

        val metricsService = MetricsService.create(vertx)


        //写法1  http://127.0.0.1/api/
        router.route("/api/*").handler(){ ctx->

            //测试 Metrics
            //Vert.x
            val eventLoopSize = metricsService.getMetricsSnapshot("vertx.event-loop-size")
            val workerPoolSize = metricsService.getMetricsSnapshot("vertx.worker-pool-size")
            val clusterHost = metricsService.getMetricsSnapshot("vertx.cluster-host")
            val clusterPort = metricsService.getMetricsSnapshot("vertx.cluster-port")

            Logger.debug(eventLoopSize.toString())
            Logger.debug(workerPoolSize.toString())
            Logger.debug(clusterHost.toString())
            Logger.debug(clusterPort.toString())


            //event bus metrics
            val eventBus = metricsService.getMetricsSnapshot("vertx.eventbus")
            Logger.debug(eventBus.toString())


            //http metrics
            val httpMetric = metricsService.getMetricsSnapshot("vertx.http.servers.127.0.0.1:80")
            Logger.debug(httpMetric.toString())


            //net server metrics
            val tcpMetric = metricsService.getMetricsSnapshot("vertx.net.servers")
            Logger.debug(tcpMetric.toString())


            //pool metrics
            val poolMetric = metricsService.getMetricsSnapshot("vertx.pools")
            Logger.debug(poolMetric.toString())

            ctx.response().end(eventLoopSize.toString())
        }


        server.requestHandler(router)

        server.listen(){res->
            if(res.succeeded()){
                Logger.debug("start api server succ, $host : $port")
            }else{
                Logger.error("$host : $port ,error:"+res.cause().toString())
            }
        }
    }
}