package com.lj.gamePlay.combat.entity.ability.skill

import com.badlogic.gdx.math.Vector3
import com.lj.gamePlay.combat.entity.ability.AbilityExecution
import com.lj.gamePlay.combat.entity.CombatEntity

class SkillAbilityExecution:AbilityExecution() {

    lateinit var inputCombatEntity:CombatEntity
    lateinit var inputPoint:Vector3
    var inputDirection:Float = 0f

    override fun beginExecute() {
        super.beginExecute()
        getAbility<SkillAbility>().spelling = true
    }

    override fun endExecute() {
        getAbility<SkillAbility>().spelling = false
        super.endExecute()
    }

}