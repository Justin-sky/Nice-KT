package com.lj.gamePlay.combat.attribute

import com.lj.core.ecs.Component

/**
 * 战斗属性数值组件，在这里管理所有角色战斗属性数值的存储、变更、刷新等
 */
class AttributeComponent: Component() {

    private val attributeNumerics = mutableMapOf<String, FloatNumberic>()

    override fun setup(){
        this.initialize()
    }

    fun initialize(){
        this.addNumeric(AttributeType.HealthPoint.name, 99_999f)
        this.addNumeric(AttributeType.AttackPower.name, 1000f)
        this.addNumeric(AttributeType.AttackDefense.name, 300f)
        this.addNumeric(AttributeType.CriticalProbability.name, .5f)

    }

    fun addNumeric(type:String, baseValue:Float): FloatNumberic {
        val numeric = FloatNumberic()
        numeric.baseValue = baseValue

        this.attributeNumerics[type] = numeric
        return numeric
    }
}