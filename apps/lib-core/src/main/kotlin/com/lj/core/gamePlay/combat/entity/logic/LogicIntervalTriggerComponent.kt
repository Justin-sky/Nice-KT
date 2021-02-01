package com.lj.core.gamePlay.combat.entity.logic

import com.lj.core.ecs.Component
import com.lj.core.gamePlay.combat.entity.ability.status.StatusAbility
import com.lj.core.gamePlay.helper.ExpressionHelper
import com.lj.core.gamePlay.combat.timer.GameTimer

/**
 * 逻辑间隔触发组件
 */
class LogicIntervalTriggerComponent :Component(){

    override var enable:Boolean = true
    lateinit var intervalTimer: GameTimer

    override fun setup() {
        val intervalStr = getEntityT<LogicEntity>().effect.interval
        val expression = ExpressionHelper.expressionParser.evaluteExpression(intervalStr!!)
        if (expression.parameters.containsKey("技能等级")){
            val level = getEntityT<LogicEntity>().getParentT<StatusAbility>().level
            expression.parameters["技能等级"]?.value = level.toDouble()
        }
        val interval = expression.value/1000f
        intervalTimer = GameTimer(interval.toFloat())
    }

    override fun update() {
        val deltaTime = 0.1f
        intervalTimer.updateAsRepeat(deltaTime){
            getEntityT<LogicEntity>().applyEffect()
        }
    }

}