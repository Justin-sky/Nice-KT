package com.lj.gamePlay.combat.entity.logic

import com.lj.core.ecs.Component
import com.lj.gamePlay.combat.entity.action.CombatAction
import com.lj.gamePlay.combat.entity.ability.status.StatusAbility

/**
 * 逻辑行动点触发组件
 */
class LogicActionTriggerComponent:Component() {

    override fun setup() {
        val actionPointType = getEntityT<LogicEntity>().effect.actionPointType
        getEntityT<LogicEntity>().getParentT<StatusAbility>().ownerEntity.listenActionPoint(actionPointType!!){ combatAction ->
            this.onActionPointTrigger(combatAction)
        }
    }

    private fun onActionPointTrigger(combatAction: CombatAction):Unit{
        getEntityT<LogicEntity>().applyEffect()
    }
}