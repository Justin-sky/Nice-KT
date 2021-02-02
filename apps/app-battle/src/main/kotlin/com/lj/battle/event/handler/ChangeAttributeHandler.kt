package com.lj.battle.event.handler

import com.lj.battle.event.EventIdType
import com.lj.core.event.AEvent1
import com.lj.core.event.EventAnnotation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kt.scaffold.tools.logger.Logger

@EventAnnotation(EventIdType.changeAttribute)
class ChangeAttributeHandler : AEvent1<String>() {

    override fun run(a: String) {
        GlobalScope.launch {
            for (i in 0..10000){

            }
            Logger.debug("change attribute...$a")
        }

    }


}