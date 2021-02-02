package com.lj.battle.msg.handler

import com.lj.core.common.ServiceHandlerAnnotation
import com.lj.core.net.Opcode
import com.lj.core.service.Msg
import com.lj.core.service.handler.BaseHandler
import io.vertx.core.AsyncResult
import io.vertx.core.Handler

@ServiceHandlerAnnotation(opcode = Opcode.MSG_C2GS_Test)
class TestBattleHandler:BaseHandler() {
    override fun process(msg: Msg, handler: Handler<AsyncResult<Msg>>) {
        //处理战斗消息




    }


}