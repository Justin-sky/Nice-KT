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

        //写法1  http://127.0.0.1/api/
        router.route("/api/*").handler(){ ctx->



            ctx.response().end("Hello, api")
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