package com.lj.core.service.handler

import com.lj.core.service.Msg
import io.vertx.core.AsyncResult
import io.vertx.core.Handler

abstract class BaseHandler{
    abstract  fun process(msg: Msg,handler: Handler<AsyncResult<Msg>>);
}