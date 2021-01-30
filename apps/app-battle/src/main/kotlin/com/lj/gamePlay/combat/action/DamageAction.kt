package com.lj.gamePlay.combat.action

import com.lj.gamePlay.config.effect.DamageEffect

/**
 * 伤害行动
 */
class DamageAction:CombatAction() {
    override var actionType = ActionType.CauseDamage

    lateinit var damageEffect:DamageEffect
    lateinit var damageSource:DamageSource //伤害来源
    var damageValue:Int = 0         //伤害数值
    var isCritical:Boolean = false  //是否是暴击

    private fun parseDamage(){

    }

    ////前置处理
    private fun preProcess(){

    }

    //应用伤害
    fun applyDamage(){
        preProcess()

        postProcess()
    }

    //后置处理
    private fun postProcess(){
        //触发 造成伤害后 行动点
    }

    enum class DamageSource{
        Attack,//普攻
        Skill,//技能
        Buff,//Buff
    }
}