package com.lj.services.msg

import com.lj.core.net.SocketManager
import com.lj.core.net.msg.BaseHandler
import com.lj.core.net.msg.Handler
import com.lj.core.net.msg.Msg
import com.lj.core.net.msg.Opcode
import com.lj.proto.Login
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kt.scaffold.tools.logger.Logger

@Handler(opcode = Opcode.MSG_C2G_LoginGate)
class LoginGateHandler:BaseHandler() {
    override fun process(socketId: String, msg: Msg) {
        GlobalScope.launch {
            //解析消息
            var rev = msg.protoStr.toByteArray()
            var c2g = Login.C2G_LoginGate.parseFrom(rev)
            Logger.debug("gateid: ${c2g.gateId}, key: ${c2g.key}")

            //返回消息
            var builder = Login.G2C_LoginGate.newBuilder()
            builder.message = "Login Gate Success .."
            builder.playerId = 991
            builder.error = 0
            var buf = builder.build().toByteArray()

            //此处 给玩家分配 游戏服ID

            SocketManager.sendMsg(socketId, msg.seq, Opcode.MSG_G2C_LoginGate, msg.serverId, msg.serverType, buf)

        }


    }
}