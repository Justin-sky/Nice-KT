package com.lj.gamePlay.combat.action

import com.lj.gamePlay.combat.skill.SkillAbility
import com.lj.gamePlay.combat.skill.SkillAbilityExecution

/**
 * 施放技能行动
 */
class SpellSkillAction:CombatAction() {
    override var actionType = ActionType.SpellSkill

    var skillID:Int = 0
    lateinit var skillAbility: SkillAbility
    var skillAbilityExecution: SkillAbilityExecution?=null

    //前置处理
    private fun preProcess(){

    }

    fun spellSkill(){
        preProcess()

        if (skillAbilityExecution == null){
            skillAbility.applyAbilityEffectsTo(target)
        }else{
            skillAbilityExecution?.beginExecute()
        }

        postProcess()
    }

    ////后置处理
    private fun postProcess(){

    }
}