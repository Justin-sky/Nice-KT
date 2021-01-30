package com.lj.gamePlay.combat.status

import com.lj.core.ecs.Component
import com.lj.gamePlay.combat.timer.GameTimer

/**
 * 状态的生命周期组件
 */
class StatusLifeTimeComponent:Component() {

    override var enable: Boolean= true
    lateinit var lifeTimer: GameTimer

    override fun setup() {
        val status = entity as StatusAbility
        val timer = status.statusConfigObject.duration/1000f
        lifeTimer = GameTimer(timer)
    }

    override fun update() {
        if (lifeTimer.isRunning){
            val deltaTime = 0.1f
            lifeTimer.updateAsFinish(deltaTime){
                getEntityT<StatusAbility>().endAbility()
            }
        }
    }
}