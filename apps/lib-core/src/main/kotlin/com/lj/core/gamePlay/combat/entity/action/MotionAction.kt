package com.lj.core.gamePlay.combat.entity.action

/**
 * 动作行动
 */
class MotionAction:CombatAction() {
    override var actionType = ActionType.Motion

    var motionType:Int = 0

    ////前置处理
    override fun preProcess() {

    }

    override fun process() {

    }

    //后置处理
    override fun postProcess() {

    }

}