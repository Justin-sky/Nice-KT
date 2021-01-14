package com.lj.services.impl

import com.lj.core.net.Opcode
import com.lj.core.net.msg.Msg
import com.lj.proto.Benchmark
import com.lj.services.BenchmarkService
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import kt.crypto.decodeBase64
import kt.crypto.encodeBase64
import kt.scaffold.tools.logger.Logger
import java.nio.charset.Charset
import java.util.*


class BenchmarkServiceImpl: BenchmarkService {

    override fun test(msg: Msg, handler:Handler<AsyncResult<Msg>>) {

        //收到的消息
        val c2gs = Benchmark.C2GS_Test.parseFrom(msg.base64Msg.decodeBase64())
        Logger.debug("benchark recv: ${c2gs.testID} - ${c2gs.testName}")

        //发出的消息
        val builder = Benchmark.GS2C_Test.newBuilder()
        builder.setError(0)
        builder.setMessage("success")
        builder.setTestResponse("I am back...")

        val gs2c = builder.build().toByteArray().encodeBase64()
        msg.base64Msg = gs2c
        msg.msgId = Opcode.MSG_GS2C_Test

        handler.handle(Future.succeededFuture(msg))
    }
}