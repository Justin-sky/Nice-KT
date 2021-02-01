package com.lj.gamePlay.combat.entity.ability

import com.lj.core.ecs.Entity
import com.lj.gamePlay.combat.entity.CombatEntity
import com.lj.gamePlay.config.SkillConfigObject
import com.lj.gamePlay.config.StatusConfigObject
import com.lj.gamePlay.config.effect.Effect

/**
 * 能力实体，存储着某个英雄某个能力的数据和状态
 */
abstract class AbilityEntity :Entity(){

    val ownerEntity: CombatEntity
        get() {
            return this.parent as CombatEntity
        }

    lateinit var configObject:Any

    var level:Int = 1

    override fun awake(initData: Any) {
        this.configObject = initData
    }

    //尝试激活能力
    open fun tryActivateAbility(){
        activateAbility()
    }

    //激活能力
    open fun activateAbility(){

    }

    //结束能力
    open fun endAbility(){
        destroy(this)
    }

    //创建能力执行体
    open fun createAbilityExecution():AbilityExecution?{
        return null
    }

    //应用效果
    fun applyEffectTo(targetEntity:CombatEntity, effectItem:Effect){

    }

    //应用能力效果
    open fun applyAbilityEffectsTo(targetEntity: CombatEntity){

        var effects: List<Effect>? = null

        if (configObject is SkillConfigObject){
            effects = (configObject as SkillConfigObject).effects
        }else if (configObject is StatusConfigObject){
            effects = (configObject as StatusConfigObject).effects
        }
        if (effects.isNullOrEmpty()) return

        for (effectItem in effects){
            applyEffectTo(targetEntity, effectItem)
        }
    }
}