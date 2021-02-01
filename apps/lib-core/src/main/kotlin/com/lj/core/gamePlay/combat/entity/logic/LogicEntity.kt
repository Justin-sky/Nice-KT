package com.lj.core.gamePlay.combat.entity.logic

import com.lj.core.ecs.Entity
import com.lj.core.gamePlay.combat.entity.action.DamageAction
import com.lj.core.gamePlay.combat.entity.CombatEntity
import com.lj.core.gamePlay.combat.entity.ability.status.StatusAbility
import com.lj.core.gamePlay.config.effect.DamageEffect
import com.lj.core.gamePlay.config.effect.Effect

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
            damageAction?.applyAction()
        }else{
            getParentT<StatusAbility>().applyEffectTo(getParentT<StatusAbility>().ownerEntity, effect)
        }
    }
}