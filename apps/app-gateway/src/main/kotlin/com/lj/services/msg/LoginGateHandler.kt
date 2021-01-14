package com.lj.services.msg

import com.lj.core.net.Opcode
import com.lj.core.net.SocketManager
import com.lj.core.net.msg.BaseHandler
import com.lj.core.net.msg.Handler
import com.lj.core.net.msg.Msg
import com.lj.proto.Login
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kt.crypto.decodeBase64
import kt.scaffold.net.DiscoveryManager
import kt.scaffold.tools.logger.Logger

@Handler(opcode = Opcode.MSG_C2G_LoginGate)
class LoginGateHandler:BaseHandler() {
    override fun process(socketId: String, msg: Msg) {
        GlobalScope.launch {
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

            //此处 给玩家分配 游戏服ID
            //分配 游戏服ID
            val serverType = 1
            var record = DiscoveryManager.chooseServerRecord(serverType)
            if(record == null) {
                Logger.error("分配游戏服失败")

                SocketManager.sendMsg(
                    socketId,
                    msg.seq,
                    Opcode.MSG_G2C_LoginGate,
                    msg.serverId,
                    serverType.toByte(),
                    buf)

            }else{
                val serverID =  record.metadata.getInteger("server_id")
                Logger.debug("分配游戏服成功： $serverID")

                SocketManager.sendMsg(
                    socketId,
                    msg.seq,
                    Opcode.MSG_G2C_LoginGate,
                    serverID.toShort(),
                    serverType.toByte(),
                    buf)

            }


        }


    }
}