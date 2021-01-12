package com.lj.services.impl

import com.lj.services.GameServerService
import com.lj.core.msg.Msg
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import kt.scaffold.tools.logger.Logger


class GameServerServiceImpl: GameServerService {

    override fun dispatchMsg(msg: Msg, handler:Handler<AsyncResult<String>>) {

        Logger.debug("game server impl:"+ msg.msgId.toString())

        handler.handle(Future.succeededFuture("echo: ${msg.msgId}"))
    }
}