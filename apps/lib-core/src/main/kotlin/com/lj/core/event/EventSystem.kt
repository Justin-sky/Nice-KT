package com.lj.core.event

import com.lj.core.common.CmdExecutor
import com.lj.core.common.HandlerAnnotation
import com.lj.core.net.msg.MessageDispatcher
import com.lj.core.service.Msg
import com.lj.core.utils.ClassScanner
import kt.scaffold.tools.logger.Logger
import java.lang.Exception
import java.lang.RuntimeException

object EventSystem {

    private val allEvents = mutableMapOf<String, MutableList<IEvent>>()

    fun initialize(packageName:String){
        val messageCommandClasses = ClassScanner.listClassesWithAnnotation(packageName, EventAnnotation::class.java)
        for(cls in messageCommandClasses){
            try{
                val handler = cls.getDeclaredConstructor().newInstance()
                val eventID = cls.getAnnotation(EventAnnotation::class.java).eventID

                this.registerEvent(eventID, handler as IEvent)

            }catch (e: Exception){
                Logger.error("initialize error: ${e.cause}")
            }
        }
    }

    private fun registerEvent(eventID:String, e:IEvent){
        if (!this.allEvents.containsKey(eventID)){
            this.allEvents[eventID] = mutableListOf()
        }
        this.allEvents[eventID]!!.add(e)
    }

    fun run(eventID:String){
        this.allEvents[eventID]?.forEach{e ->
            e.handle()
        }
    }

    fun <A> run(eventID:String, a:A){
        this.allEvents[eventID]?.forEach{ e ->
            e.handle(a!!)
        }
    }

    fun <A, B> run(eventID:String, a:A, b:B){
        this.allEvents[eventID]?.forEach{ e ->
            e.handle(a!!, b!!)
        }
    }

    fun <A, B, C> run(eventID:String, a:A, b:B, c:C){
        this.allEvents[eventID]?.forEach{ e ->
            e.handle(a!!, b!!, c!!)
        }
    }

    fun <A, B, C, D> run(eventID:String, a:A, b:B, c:C, d:D){
        this.allEvents[eventID]?.forEach{ e ->
            e.handle(a!!, b!!, c!!, d!!)
        }
    }
}