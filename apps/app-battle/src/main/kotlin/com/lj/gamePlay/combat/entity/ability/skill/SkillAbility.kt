package com.lj.gamePlay.combat.entity.ability.skill

import com.lj.gamePlay.combat.entity.ability.AbilityEntity
import com.lj.gamePlay.config.SkillConfigObject
import com.lj.gamePlay.config.SkillSpellType
import com.lj.gamePlay.combat.timer.GameTimer

abstract class SkillAbility:AbilityEntity() {

    lateinit var skillConfigObject: SkillConfigObject
    var spelling:Boolean = false
    val cooldownTimer: GameTimer =
        GameTimer(1f)

    override fun awake(initData: Any) {
        super.awake(initData)

        skillConfigObject = initData as SkillConfigObject
        if (skillConfigObject.skillSpellType == SkillSpellType.Passive){
            tryActivateAbility()
        }
    }
}