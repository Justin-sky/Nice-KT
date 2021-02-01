package com.lj.core.gamePlay.combat.attribute

import com.lj.core.ecs.Component

/**
 * 战斗属性数值组件，在这里管理所有角色战斗属性数值的存储、变更、刷新等
 */
class AttributeComponent: Component() {

    private val attributeNumerics = mutableMapOf<String, FloatNumberic>()

    val moveSpeed:FloatNumberic
        get() {
            return attributeNumerics[AttributeType.MoveSpeed.name]!!
        }
    val causeDamage:FloatNumberic
        get() {
            return attributeNumerics[AttributeType.CauseDamage.name]!!
        }
    val healthPoint:FloatNumberic
        get() {
            return attributeNumerics[AttributeType.HealthPoint.name]!!
        }
    val attackPower:FloatNumberic
        get() {
            return attributeNumerics[AttributeType.AttackPower.name]!!
        }
    val attackDefense:FloatNumberic
        get() {
            return attributeNumerics[AttributeType.AttackDefense.name]!!
        }
    val criticalProbability:FloatNumberic
        get() {
            return attributeNumerics[AttributeType.CriticalProbability.name]!!
        }

    override fun setup(){
        this.initialize()
    }

    private fun initialize(){
        this.addNumeric(AttributeType.HealthPoint, 99_999f)
        this.addNumeric(AttributeType.MoveSpeed, 1f)
        this.addNumeric(AttributeType.CauseDamage, 1f)
        this.addNumeric(AttributeType.AttackPower, 1000f)
        this.addNumeric(AttributeType.AttackDefense, 300f)
        this.addNumeric(AttributeType.CriticalProbability, .5f)

    }

    fun addNumeric(attributeType: AttributeType, baseValue:Float): FloatNumberic {
        val numeric = FloatNumberic()
        numeric.baseValue = baseValue
        this.attributeNumerics[attributeType.name] = numeric
        return numeric
    }

    fun getNumeric(attributeName:String):FloatNumberic{
        return attributeNumerics[attributeName]!!
    }
}