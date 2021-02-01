package com.lj.gamePlay.combat.entity.ability.status

import com.badlogic.gdx.math.Vector3
import com.lj.gamePlay.combat.entity.ability.AbilityExecution
import com.lj.gamePlay.combat.entity.CombatEntity

abstract class StatusAbilityExecution:AbilityExecution() {

    lateinit var inputCombatEntity: CombatEntity
    lateinit var inputPoint:Vector3
    var inputDirection:Float = 0f
}