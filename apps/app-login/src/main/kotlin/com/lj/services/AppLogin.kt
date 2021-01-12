package com.lj.services

import com.lj.core.msg.MessageDispatcher
import com.lj.core.msg.Msg
import com.lj.proto.OpcodeOuterClass

suspend fun main() {

    MessageDispatcher.initialize("com.lj.services.msg")

    MessageDispatcher.dispatch(Msg(1,
        OpcodeOuterClass.Opcode.MSG_C2G_LoginGate_VALUE,
        1,
        ""))
}