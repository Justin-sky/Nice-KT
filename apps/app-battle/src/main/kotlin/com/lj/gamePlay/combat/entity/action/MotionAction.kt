package com.lj.gamePlay.combat.entity.action

/**
 * 动作行动
 */
class MotionAction:CombatAction() {
    override var actionType = ActionType.Motion

    var motionType:Int = 0

    ////前置处理
    private fun preProcess() {

    }

    fun applyMotion(){
        preProcess()

        postProcess()
    }

    //后置处理
    private fun postProcess() {

    }

}