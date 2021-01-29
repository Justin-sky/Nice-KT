package com.lj.gamePlay.combat

import com.lj.core.ecs.Component
import com.lj.core.ecs.Entity
import com.lj.gamePlay.combat.action.CombatAction
import com.lj.gamePlay.combat.combatEntity.CombatEntity

/**
 * 战斗行动管理组件
 */
class CombatActionManageComponent:Component() {

    val combatActions = mutableListOf<CombatAction>()

    inline fun <reified T> createAction(combatEntity: CombatEntity) :T where T:CombatAction{
        val action = Entity.createWithParent<T>(getEntityT<CombatContext>())
        action.creator = combatEntity

        combatActions.add(action)
        return action
    }


}