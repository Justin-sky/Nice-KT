package com.lj.services.msg

import com.lj.core.msg.BaseHandler
import com.lj.core.msg.Handler
import com.lj.core.msg.Msg
import com.lj.proto.OpcodeOuterClass
import kt.scaffold.tools.logger.Logger

@Handler(opcode = OpcodeOuterClass.Opcode.MSG_C2G_LoginGate_VALUE)
class LoginHandler: BaseHandler() {

    override fun process(msg: Msg) {
        Logger.debug("hello....")
    }

}