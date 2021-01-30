package com.lj.gamePlay.combat.action

import com.lj.gamePlay.config.effect.CureEffect

/**
 * 治疗行动
 */
class CureAction :CombatAction() {
    override var actionType = ActionType.GiveCure

    var cureEffect:CureEffect?=null
    var cureValue:Int = 0   //治疗数值

    //前置处理
    private fun preProcess(){
        cureValue = cureEffect?.cureValueFormula?.toInt() ?: 0
    }

    fun applyCure(){
        preProcess()
        target.receiveCure(this)
        postProcess()
    }

    //后置处理
    private fun postProcess(){
        creator.triggerActionPoint(ActionPointType.PostGiveCure, this)
        target.triggerActionPoint(ActionPointType.PostGiveCure, this)
    }
}