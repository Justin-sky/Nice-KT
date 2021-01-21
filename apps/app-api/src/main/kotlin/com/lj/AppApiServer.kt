package com.lj

import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServerOptions
import io.vertx.ext.web.Router
import kt.scaffold.Application
import kt.scaffold.tools.logger.Logger

suspend fun main() {

    //初始化Vertx
    Application.setupVertx()

    //启动Game Server
    Application.deployVerticle(ApiServerVerticle::class.java.name,"ApiServerVerticle")


}