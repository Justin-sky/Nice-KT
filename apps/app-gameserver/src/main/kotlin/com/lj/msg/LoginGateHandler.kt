package com.lj.msg

import com.lj.core.common.ServiceHandlerAnnotation
import com.lj.core.net.Opcode
import com.lj.core.service.Msg
import com.lj.core.service.handler.BaseHandler
import com.lj.proto.Login
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kt.crypto.decodeBase64
import kt.crypto.encodeBase64
import kt.scaffold.tools.logger.Logger

@ServiceHandlerAnnotation(opcode = Opcode.MSG_C2G_LoginGate)
class LoginGateHandler: BaseHandler() {
    override fun process(msg: Msg,handler: Handler<AsyncResult<Msg>>) {
        //解析消息
        var rev = msg.base64Msg.decodeBase64()
        var c2g = Login.C2G_LoginGate.parseFrom(rev)
        Logger.debug("gateid: ${c2g.gateId}, key: ${c2g.key}")

        //返回消息
        var builder = Login.G2C_LoginGate.newBuilder()
        builder.message = "Login Gate Success .."
        builder.playerId = 991
        builder.error = 0
        var buf = builder.build().toByteArray()

        val resResp = msg.copy()
        msg.msgId = Opcode.MSG_G2C_LoginGate
        msg.base64Msg = buf.encodeBase64()

        //登录到服务器后，发用户ID返回
        msg.userId = 991

        handler.handle(Future.succeededFuture(msg))

    }
}
