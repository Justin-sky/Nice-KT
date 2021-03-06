package com.lj.core.gamePlay.combat.entity.action

import com.lj.core.ecs.Entity
import com.lj.core.gamePlay.combat.entity.CombatEntity

/**
 *  动作作用目标
 */
enum class ActionTargetType {
    Instigator,
    Target
}

enum class ActionType{
    SpellSkill, //施放技能
    CauseDamage, //造成伤害
    GiveCure, //给予治疗
    AssignEffect, //赋给效果
    Motion  //动作行动
}

/**
 * 战斗行动概念，造成伤害、治疗英雄、赋给效果等属于战斗行动，需要继承自CombatAction
 * 战斗行动由战斗实体主动发起，包含本次行动所需要用到的所有数据，并且会触发一系列行动点事件 <see cref="ActionPoint"/>
 */
abstract class CombatAction : Entity() {

    abstract var actionType: ActionType

    lateinit var creator: CombatEntity
    lateinit var target: CombatEntity

    protected open fun preProcess() {

    }

    protected open fun process(){

    }

    protected open fun postProcess() {

    }

    fun applyAction() {
        this.preProcess()
        this.process()
        this.postProcess()
    }

}