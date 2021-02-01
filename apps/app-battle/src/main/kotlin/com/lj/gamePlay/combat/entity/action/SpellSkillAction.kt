package com.lj.gamePlay.combat.entity.action

import com.lj.gamePlay.combat.entity.ability.skill.SkillAbility
import com.lj.gamePlay.combat.entity.ability.skill.SkillAbilityExecution

/**
 * 施放技能行动
 */
class SpellSkillAction:CombatAction() {
    override var actionType = ActionType.SpellSkill

    var skillID:Int = 0
    lateinit var skillAbility: SkillAbility
    var skillAbilityExecution: SkillAbilityExecution?=null

    //前置处理
    override fun preProcess(){

    }

    override fun process(){

        if (skillAbilityExecution == null){
            skillAbility.applyAbilityEffectsTo(target)
        }else{
            skillAbilityExecution?.beginExecute()
        }

    }

    ////后置处理
    override fun postProcess(){

    }
}