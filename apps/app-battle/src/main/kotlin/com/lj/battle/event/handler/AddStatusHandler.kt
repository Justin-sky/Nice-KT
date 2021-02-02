package com.lj.battle.event.handler

import com.lj.battle.event.EventIdType
import com.lj.core.event.AEvent1
import com.lj.core.event.EventAnnotation
import kt.scaffold.tools.logger.Logger

@EventAnnotation(EventIdType.addStatus)
class AddStatusHandler : AEvent1<String>() {

    override fun run(a: String) {
        Logger.debug("add status ")
    }
}