package com.lj.gamePlay.combat.attribute

enum class AttributeType(att: Int) {
    None(0),
    HealthPoint(1000), //生命值
    AttackPower(1001), //攻击力
    AttackDefense(1002), //护甲值
    SpellPower(1003), //法术强度
    MagicDefense(1004), //魔法抗性

    CriticalProbability(2001), //暴击概率
    MoveSpeed(2002), //移动速度
    AttackSpeed(2003), //攻击速度

    ShieldValue(3001), //护盾值

    CauseDamage(4001), //造成伤害

}