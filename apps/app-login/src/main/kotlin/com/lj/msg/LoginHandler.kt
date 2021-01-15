package com.lj.msg

import com.lj.core.common.HandlerAnnotation
import com.lj.core.net.SocketManager
import com.lj.core.net.msg.BaseHandler
import com.lj.core.service.Msg
import com.lj.core.net.Opcode
import com.lj.proto.Login
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kt.crypto.decodeBase64
import kt.scaffold.tools.logger.Logger

@HandlerAnnotation(opcode = Opcode.MSG_C2R_Login)
class LoginHandler: BaseHandler() {

    override  fun process(socketId:String, msg: Msg) {

        GlobalScope.launch {
            //解析消息
            var rev = msg.base64Msg.decodeBase64()
            var c2r = Login.C2R_Login.parseFrom(rev)
            Logger.debug("account: ${c2r.account}, passwd: ${c2r.password}")

            //返回消息
            var builder = Login.R2C_Login.newBuilder()
            builder.address = "127.0.0.1:9000"
            builder.key = 123456
            builder.gateId = 100

            var buf = builder.build().toByteArray()

            SocketManager.sendMsg(
                socketId,
                msg.seq,
                Opcode.MSG_R2C_Login,
                msg.serverId,
                msg.serverType,
                buf)

        }
    }
}