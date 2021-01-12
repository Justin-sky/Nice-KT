package com.lj.services.msg

import com.lj.core.net.SocketManager
import com.lj.core.net.msg.BaseHandler
import com.lj.core.net.msg.Handler
import com.lj.core.net.msg.Msg
import com.lj.proto.OpcodeOuterClass
import kt.scaffold.tools.logger.Logger

@Handler(opcode = OpcodeOuterClass.Opcode.MSG_C2G_LoginGate_VALUE)
class LoginHandler: BaseHandler() {

    override suspend fun process(socketId:String, msg: Msg) {
        Logger.debug("hello....")

        var buf = msg.protoStr.toByteArray()
        SocketManager.sendMsg(socketId, msg.seq, msg.msgId, msg.serverId, buf)
    }

}