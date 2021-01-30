package com.lj.gamePlay.combat.condition

import com.lj.gamePlay.combat.action.ActionPointType
import com.lj.gamePlay.combat.action.CombatAction
import com.lj.gamePlay.combat.entity.CombatEntity
import com.lj.gamePlay.combat.timer.GameTimer

class WhenInTimeNoDamageCondition : ConditionEntity() {
    private lateinit var noDamageTimer: GameTimer

    override fun awake(initData: Any) {
        val time = initData.toString().toFloat()
        noDamageTimer = GameTimer(time)
        getParentT<CombatEntity>().listenActionPoint(ActionPointType.PostReceiveDamage){combatAction->
            whenReceiveDamage(combatAction)
        }
    }

    suspend fun startListen(whenNoDamageInTimeCallback:()->Unit){
        while (true){
            if (isDisposed){
                break
            }
            // await ET.TimerComponent.Instance.WaitAsync(100);
            noDamageTimer.updateAsFinish(0.1f,whenNoDamageInTimeCallback)
        }
    }

    private fun whenReceiveDamage(combatAction: CombatAction){
        noDamageTimer.reset()
    }
}