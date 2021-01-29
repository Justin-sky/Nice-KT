package com.lj.gamePlay.combat.entity

import com.lj.core.ecs.Entity
import com.lj.gamePlay.combat.attribute.HealthPoint
import com.lj.gamePlay.combat.skill.SkillAbility
import com.lj.gamePlay.combat.status.StatusAbility

/**
 *  战斗实体
 */
class CombatEntity:Entity() {

    val currentHealth:HealthPoint = HealthPoint()
    val nameSkills = mutableMapOf<String, SkillAbility>()
    val typeIdStatuses = mutableMapOf<String, MutableList<SkillAbility>>()
    val typeStatuses = mutableMapOf<Any, MutableList<SkillAbility>>()



}

class RemoveStatusEvent{
    var combatEntity:CombatEntity?=null
    var status: StatusAbility?=null
    var statusId:Long = 0

}