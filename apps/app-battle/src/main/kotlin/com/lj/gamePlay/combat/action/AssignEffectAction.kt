package com.lj.gamePlay.combat.action

import com.lj.gamePlay.combat.ability.AbilityEntity
import com.lj.gamePlay.combat.status.StatusAbility
import com.lj.gamePlay.combat.status.StatusLifeTimeComponent
import com.lj.gamePlay.config.BattleConfigManager
import com.lj.gamePlay.config.effect.AddStatusEffect
import com.lj.gamePlay.config.effect.DamageEffect
import com.lj.gamePlay.config.effect.Effect

/**
 * 赋给效果行动
 */
class AssignEffectAction:CombatAction() {
    override var actionType = ActionType.AssignEffect

    ////创建这个赋给效果行动的源能力
    lateinit var sourceAbility:AbilityEntity
    lateinit var effect:Effect
    lateinit var status:StatusAbility

    ////前置处理
    private fun preProcess(){

    }

    fun applyAssignEffect(){
        preProcess()

        if (effect is DamageEffect){

        }

        if (effect is AddStatusEffect){
            val addStatusEffect = effect as AddStatusEffect
            val addStatus = addStatusEffect.addStatus

            val statusConfig = BattleConfigManager.getStatusConfig(addStatus!!)
            if (statusConfig!!.canStack){
                if (target.hasStatus(status._id.toString())){
                    val status = target.getStatus(statusConfig.id.toString())
                    val statusLifeTimer = status?.getComponent<StatusLifeTimeComponent>()?.lifeTimer
                    statusLifeTimer!!.maxTime = addStatusEffect.duration/1000f
                    statusLifeTimer.reset()
                    return
                }
            }
            status = target.attachStatus(statusConfig)
            status.caster = creator
            status.level = sourceAbility.level
            status.addComponent<StatusLifeTimeComponent>()
            status.tryActivateAbility()
        }

        postProcess()
    }

    ////后置处理
    private fun postProcess(){
        if (effect is AddStatusEffect){
            creator.triggerActionPoint(ActionPointType.PostGiveStatus, this)
            target.triggerActionPoint(ActionPointType.PostReceiveStatus, this)
        }
    }

    enum class EffectType{
        DamageAffect,
        NumericModify,
        StatusAttach,
        BuffAttach
    }
}