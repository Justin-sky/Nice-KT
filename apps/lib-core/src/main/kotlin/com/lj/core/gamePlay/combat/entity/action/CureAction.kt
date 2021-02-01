package com.lj.core.gamePlay.combat.entity.action

import com.lj.core.gamePlay.config.effect.CureEffect

/**
 * 治疗行动
 */
class CureAction :CombatAction() {
    override var actionType = ActionType.GiveCure

    var cureEffect:CureEffect?=null
    var cureValue:Int = 0   //治疗数值

    override fun preProcess() {
        cureValue = cureEffect?.cureValueFormula?.toInt() ?: 0
    }

    override fun process() {
        target.receiveCure(this)
    }

    override fun postProcess() {
        creator.triggerActionPoint(ActionPointType.PostGiveCure, this)
        target.triggerActionPoint(ActionPointType.PostGiveCure, this)
    }

}