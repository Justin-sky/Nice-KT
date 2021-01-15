package com.lj.core.service.handler

import com.lj.core.common.CmdExecutor
import com.lj.core.common.ServiceHandlerAnnotation
import com.lj.core.service.Msg
import com.lj.core.utils.ClassScanner
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import kt.scaffold.tools.logger.Logger

object ServiceDispatcher {

    private val cmdHandlers = mutableMapOf<Short, CmdExecutor>()

    fun initialize(packageName:String){
        val messageCommandClasses = ClassScanner.listClassesWithAnnotation(packageName,
            ServiceHandlerAnnotation::class.java)
        for(cls in messageCommandClasses){
            try{
                val handler = cls.getDeclaredConstructor().newInstance()
                val method = cls.getMethod("process", Msg::class.java, Handler::class.java)
                val msgId = cls.getAnnotation(ServiceHandlerAnnotation::class.java).opcode

                var cmdExecutor = cmdHandlers.get(msgId)
                if (cmdExecutor!=null) throw RuntimeException("cmd[$msgId] duplicated")

                cmdExecutor = CmdExecutor(method, handler as Object)
                cmdHandlers.put(msgId, cmdExecutor)

            }catch (e:Exception){
                Logger.error("initialize error: ${e.cause}")
            }
        }
    }

    fun dispatch(msg: Msg, handler:Handler<AsyncResult<Msg>>){
        val cmdExecutor: CmdExecutor? = cmdHandlers.get(msg.msgId)
        if(cmdExecutor == null){
            Logger.error("message executor missed, cmd=${msg.msgId}")
            return
        }

        try {
            cmdExecutor.method.invoke(cmdExecutor.handler,msg,handler)
        }catch (e:Exception){
            Logger.error("dispatch error: ${msg.msgId}, ${e.cause}")
        }
    }
}