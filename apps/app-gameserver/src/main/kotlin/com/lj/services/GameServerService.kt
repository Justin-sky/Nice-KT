package com.lj.services

import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.AsyncResult
import io.vertx.core.Handler

@VertxGen
@ProxyGen
interface GameServerService{

    fun dispatchMsg(
        msg: Msg,
        handler:Handler<AsyncResult<String>>
    )


}