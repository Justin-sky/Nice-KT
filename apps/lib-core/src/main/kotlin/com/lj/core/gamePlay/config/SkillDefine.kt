package com.lj.core.gamePlay.config

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

//技能类型
enum class SkillSpellType{
    Initiative,
    Passive
}

//技能类型
enum class SkillType{
    Targeting,
    Untoward
}

//技能效果触发类型
enum class SkillEffectTriggerType{
    Targeting,
    Untoward
}

//技能目标检测方式
enum class SkillTargetSelectType{
    Auto,
    PlayerSelect,
    BodyCollideSelect,
    AreaSelect,
    ConditionSelect,
    Other
}

//区域场类型
enum class SkillAffectAreaType(p:Int){
    Circle(0),
    Rect(1),
    Compose(2)
}

//技能作用对象
enum class SkillAffectTargetType(p:Int){
    Self(0),
    SelfTeam(1),
    EnemyTeam(2)
}

//作用对象
enum class AddSkillEffetTargetType(p:Int){
    SkillTarget(0),
    Self(1)
}

//目标类型
enum class SkillTargetType(p:Int){
    Single(0),
    Multiple(1)
}

//伤害类型
enum class DamageType(p:Int){
    Physic(0),
    Magic(1),
    Real(2)
}

//效果类型
enum class SkillEffectType(p:Int){
    None(0),
    CauseDamage(1),
    CureHero(2),
    AddStatus(3),
    RemoveStatus(4),
    NumericModify(5),
    AddShield(6),
    StackTag(7),
    Poison(8),
    Burn(9)
}

//行为禁制
enum class ActionControlType(var code: Int){
    None(0),
    MoveForbid(1 shl  1),
    SkillForbid(1 shl 2),
    AttackForbid(1 shl 3),
    MoveControl(1 shl 4),
    AttackControl(1 shl 5);

    @JsonValue
    fun toValue():Int{
        return this.code
    }

    companion object{
        @JvmStatic
        @JsonCreator
        fun forValue(v:Int):ActionControlType{
            for (el in values()){
                if (el.code == v) return  el
            }
            return None
        }
    }
}

//属性类型
enum class AttributeType(var code: Int) {
    None(0),
    HealthPoint(1000),
    AttackPower(1001),
    AttackDefense(1002),
    SpellPower(1003),
    MagicDefense(1004),

    CriticalProbability(2001),
    MoveSpeed(2002),
    AttackSpeed(2003),

    ShieldValue(3001),

    CauseDamage(4001);


    @JsonValue
    fun toValue():Int{
        return this.code
    }

    companion object{
        @JvmStatic
        @JsonCreator
        fun forValue(v:Int):AttributeType{
            for (el in values()){
                if (el.code == v) return  el
            }
            return None
        }
    }
}

//修饰类型
enum class ModifyType(p:Int){
    Add(0),
    PercentAdd(1)
}













