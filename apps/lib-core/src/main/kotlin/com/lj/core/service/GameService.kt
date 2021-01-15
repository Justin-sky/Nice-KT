package com.lj.core.service

import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.AsyncResult
import io.vertx.core.Handler

@VertxGen
@ProxyGen
interface GameService{

    fun dispatch(
        msg: Msg,
        handler: Handler<AsyncResult<Msg>>
    )


}