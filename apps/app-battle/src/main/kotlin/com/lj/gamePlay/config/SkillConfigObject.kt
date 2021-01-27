package com.lj.gamePlay.config

import com.lj.gamePlay.config.effect.Effect

class SkillConfigObject {

    var id:Int = 0
    lateinit var name:String
    lateinit var skillSpellType:SkillSpellType
    lateinit var targetSelectType:SkillTargetSelectType
    lateinit var affectAreaType:SkillAffectAreaType

    var circleAreaRadius:Float = 0f

    lateinit var affectTargetType:SkillAffectTargetType

    var coldTime:Int = 0

    var effects = mutableListOf<Effect>()


}

enum class ShieldType{
    Shield,
    PhysicShield,
    MagicShield,
    SkillShield
}

enum class TagType{
    Power
}