package com.lj.gamePlay.combat.entity.action

import com.lj.core.ecs.Component

/**
 * 行动点，一次战斗行动<see cref="CombatAction"/>会触发战斗实体一系列的行动点
 */
class ActionPoint{
    var listeners = mutableListOf<(combatAction:CombatAction)->Unit>()
}

enum class ActionPointType{
    PreCauseDamage, //造成伤害前
    PreReceiveDamage, //承受伤害前
    PostCauseDamage, //造成伤害后
    PostReceiveDamage, //承受伤害后

    PostGiveCure,   //给予治疗后
    PostReceiveCure, //接受治疗后

    AssignEffect, //赋给效果
    ReceiveEffect, //接受效果

    PostGiveStatus, //赋加状态后
    PostReceiveStatus, //承受状态后

    Max,
}

/**
 * 行动点管理器，在这里管理一个战斗实体所有行动点的添加监听、移除监听、触发流程
 */
class ActionPointManageComponent: Component() {

    private var actionPoints = mutableMapOf<ActionPointType,ActionPoint>()

    override fun setup() {
        super.setup()
    }

    fun addListener(actionPointType: ActionPointType, action:(combatAction:CombatAction)->Unit){
        if(!this.actionPoints.containsKey(actionPointType)){
            this.actionPoints[actionPointType] = ActionPoint()
        }
        actionPoints[actionPointType]?.listeners?.add(action)
    }

    fun removeListener(actionPointType: ActionPointType, action:(combatAction:CombatAction)->Unit){
        actionPoints[actionPointType]?.listeners?.remove(action)
    }

    fun triggerActionPoint(actionPointType: ActionPointType, action:CombatAction){
        actionPoints[actionPointType]?.listeners?.forEach { it->
            it(action)
        }
    }

}