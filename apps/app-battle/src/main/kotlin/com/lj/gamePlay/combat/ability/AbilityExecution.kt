package com.lj.gamePlay.combat.ability

import com.lj.core.ecs.Entity
import com.lj.gamePlay.combat.combatEntity.CombatEntity

/**
 * 能力执行体，能力执行体是实际创建执行能力表现，触发应用能力效果的地方
 * 这里可以存一些表现执行相关的临时的状态数据
 */
abstract class AbilityExecution :Entity(){

    lateinit var abilityEntity:AbilityEntity

    val owenEntity:CombatEntity
        get() {
            return (this.parent as CombatEntity)
        }

    override fun awake(initData:Any) {
        this.abilityEntity = initData as AbilityEntity
    }

    //开始执行
    open fun beginExecute(){}

    //结束执行
    open fun endExecute(){
        destroy(this)
    }

    fun <T> getAbility():T where T:AbilityEntity{
        return this.abilityEntity as T
    }
}