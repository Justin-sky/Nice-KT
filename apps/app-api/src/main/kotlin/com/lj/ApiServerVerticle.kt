package com.lj

import io.vertx.core.AbstractVerticle
import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServerOptions
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

        //写法1
        router.route(HttpMethod.GET,"/test").handler(){ ctx->

            val response = ctx.response()
            response.putHeader("content-type", "text/plain")

            response.write(Buffer.buffer("Hello"))

            response.end()
        }
        //写法2
        router.get("/test1").handler { ctx->
            val response = ctx.response()
            response.putHeader("content-type", "text/plain")

            response.write(Buffer.buffer("Hello 33"))

            response.end()
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