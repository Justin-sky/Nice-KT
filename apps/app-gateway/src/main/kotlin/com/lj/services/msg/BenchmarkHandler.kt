package com.lj.services.msg

import com.lj.core.net.Opcode
import com.lj.core.net.SocketManager
import com.lj.core.net.msg.BaseHandler
import com.lj.core.net.msg.Handler
import com.lj.core.net.msg.Msg
import com.lj.proto.Benchmark
import com.lj.services.BenchmarkService
import com.lj.services.kotlin.testAwait
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kt.crypto.decodeBase64
import kt.scaffold.net.DiscoveryManager
import kt.scaffold.tools.logger.Logger

@Handler(opcode = Opcode.MSG_C2GS_Test)
class BenchmarkHandler:BaseHandler() {
    override fun process(socketId: String, msg: Msg) {
        GlobalScope.launch {
            val serverID = msg.serverId.toInt()
            var serverType = msg.serverType.toInt()


            //直接转发回客户端
            var reference = DiscoveryManager.getReference(serverID,serverType);
            if (reference!= null){
               val service = reference.getAs(BenchmarkService::class.java)

                //直接转发回客户端
                val msgResp = service.testAwait(msg)

                Logger.debug("handler: $socketId, ${msgResp.msgId}")
                SocketManager.sendMsg(
                    socketId,
                    msgResp.seq,
                    Opcode.MSG_GS2C_Test,
                    msgResp.serverId,
                    msgResp.serverType,
                    msgResp.base64Msg.decodeBase64())
            }

        }
    }
}