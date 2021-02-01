package com.lj.skills

import com.lj.core.gamePlay.combat.entity.CombatEntity
import com.lj.core.gamePlay.combat.entity.ability.AbilityExecution
import com.lj.core.gamePlay.combat.entity.ability.skill.SkillAbility
import com.lj.core.gamePlay.combat.entity.ability.skill.SkillAbilityExecution

class Skill1001Ability:SkillAbility() {

    override fun createAbilityExecution(): AbilityExecution? {
        return createWithParent<Skill1001Execution>(getParentT<CombatEntity>())
    }

}

class Skill1001Execution:SkillAbilityExecution(){

    override fun beginExecute() {
        super.beginExecute()



    }
}