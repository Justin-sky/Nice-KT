package com.lj.msg

import com.lj.core.common.ServiceHandlerAnnotation
import com.lj.core.consts.EventBusAddress
import com.lj.core.net.Opcode
import com.lj.core.net.SocketManager
import com.lj.core.service.Msg
import com.lj.core.service.handler.BaseHandler
import com.lj.proto.Benchmark
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import kt.crypto.decodeBase64
import kt.crypto.encodeBase64
import kt.scaffold.tools.logger.Logger

@ServiceHandlerAnnotation(opcode = Opcode.MSG_C2GS_Test)
class BenchmarkHandler: BaseHandler() {
    override fun process(msg: Msg,handler: Handler<AsyncResult<Msg>>) {
        //收到的消息
        val c2gs = Benchmark.C2GS_Test.parseFrom(msg.base64Msg.decodeBase64())
        Logger.debug("benchark recv: ${c2gs.testID} - ${c2gs.testName} - ${msg.seq}")


        //发出的消息
        val builder = Benchmark.GS2C_Test.newBuilder()
        builder.setError(0)
        builder.setMessage("success")
        builder.setTestResponse("I am back...")

        val gs2c = builder.build().toByteArray().encodeBase64()
        msg.base64Msg = gs2c
        msg.msgId = Opcode.MSG_GS2C_Test

        msg.userId = 991

        //推送消息
        SocketManager.sendMsg2Gateway(EventBusAddress.PUSH2CLIENT_ADDRESS,msg)



        handler.handle(Future.succeededFuture(msg))
    }
}