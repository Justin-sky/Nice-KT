package com.lj.core.net.msg

import com.lj.core.utils.ClassScanner
import kt.scaffold.tools.logger.Logger
import java.lang.Exception
import java.lang.RuntimeException

object MessageDispatcher {

    private val cmdHandlers = mutableMapOf<Int,CmdExecutor>()

    fun initialize(packageName:String){
        val messageCommandClasses = ClassScanner.listClassesWithAnnotation(packageName,Handler::class.java)
        for(cls in messageCommandClasses){
            try{
                val handler = cls.getDeclaredConstructor().newInstance()
                val method = cls.getMethod("process", Msg::class.java)
                val msgId = cls.getAnnotation(Handler::class.java).opcode

                var cmdExecutor = cmdHandlers.get(msgId)
                if (cmdExecutor!=null) throw RuntimeException("cmd[$msgId] duplicated")

                cmdExecutor = CmdExecutor(method, handler as Object)
                cmdHandlers.put(msgId, cmdExecutor)

            }catch (e:Exception){
                Logger.error("initialize error: ${e.cause}")
            }
        }
    }

    fun dispatch(socketId:String, msg:Msg){
        val cmdExecutor = cmdHandlers.get(msg.msgId)
        if(cmdExecutor == null){
            Logger.error("message executor missed, cmd=${msg.msgId}")
            return
        }

        try {
            cmdExecutor.method.invoke(cmdExecutor.handler,socketId, msg)
        }catch (e:Exception){
            Logger.error("dispatch error: ${msg.msgId}, ${e.cause}")
        }
    }
}