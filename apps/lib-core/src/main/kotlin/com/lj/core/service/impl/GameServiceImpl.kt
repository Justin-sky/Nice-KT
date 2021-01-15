package com.lj.core.service.impl

import com.lj.core.service.GameService
import com.lj.core.service.Msg
import com.lj.core.service.handler.ServiceDispatcher
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class GameServiceImpl: GameService {

    override fun dispatch(msg: Msg, handler:Handler<AsyncResult<Msg>>) {
        //开协成进行异步处理
       GlobalScope.launch {
           ServiceDispatcher.dispatch(msg,handler);
       }
    }
}