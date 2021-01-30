package com.lj.gamePlay.combat.status.logic

import com.lj.core.ecs.Component
import com.lj.core.ecs.Entity
import com.lj.gamePlay.combat.status.StatusAbility

/**
 * 逻辑条件触发组件
 */
class LogicConditionTriggerComponent :Component(){

    override fun setup() {
        val conditionType = getEntityT<LogicEntity>().effect.conditionType
        val conditionParam = getEntityT<LogicEntity>().effect.conditionParam

        entity.getParentT<StatusAbility>().ownerEntity.listenerCondition(
            conditionType!!,
            { this.onConditionTrigger() },
            conditionParam)

    }

    private fun onConditionTrigger(){
        getEntityT<LogicEntity>().applyEffect()
    }
}