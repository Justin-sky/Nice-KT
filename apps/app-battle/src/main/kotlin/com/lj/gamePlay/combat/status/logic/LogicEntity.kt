package com.lj.gamePlay.combat.status.logic

import com.lj.core.ecs.Entity
import com.lj.gamePlay.combat.action.DamageAction
import com.lj.gamePlay.combat.entity.CombatEntity
import com.lj.gamePlay.combat.status.StatusAbility
import com.lj.gamePlay.config.effect.DamageEffect
import com.lj.gamePlay.config.effect.Effect
import org.apache.zookeeper.data.Stat

class LogicEntity :Entity() {
    lateinit var effect:Effect

    override fun awake(initData: Any) {
        effect = initData as Effect
    }

    fun applyEffect(){
        if (effect is DamageEffect){
            val damageEffect = effect as DamageEffect
            val damageAction = getParentT<StatusAbility>().caster?.createCombatAction<DamageAction>()
            damageAction?.target = getParentT<StatusAbility>().getParentT<CombatEntity>()
            damageAction?.damageSource = DamageAction.DamageSource.Buff
            damageAction?.applyDamage()
        }else{
            getParentT<StatusAbility>().applyEffectTo(getParentT<StatusAbility>().ownerEntity, effect)
        }
    }
}