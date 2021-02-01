package com.lj.core.gamePlay.combat.entity.action

import com.lj.core.gamePlay.combat.attribute.AttributeComponent
import com.lj.core.gamePlay.config.effect.DamageEffect
import com.lj.core.gamePlay.helper.ExpressionHelper
import kotlin.math.max
import kotlin.random.Random

/**
 * 伤害行动
 */
class DamageAction:CombatAction() {
    override var actionType = ActionType.CauseDamage

    lateinit var damageEffect:DamageEffect
    lateinit var damageSource:DamageSource //伤害来源
    var damageValue:Int = 0         //伤害数值
    var isCritical:Boolean = false  //是否是暴击

    private fun parseDamage():Int{
        val express = ExpressionHelper.expressionParser.evaluteExpression(damageEffect.damageValueFormula!!)

        val power = creator.getComponent<AttributeComponent>()?.attackPower?.value
        express.parameters["自身攻击力"]?.value = power!!.toDouble()

        return express.value.toInt()
    }

    ////前置处理
    override fun preProcess(){
        if (damageSource == DamageSource.Attack){
            var prob = creator.getComponent<AttributeComponent>()?.criticalProbability?.value
            isCritical = Random(100).nextInt()/100f < prob!!

            val attack:Float = creator.getComponent<AttributeComponent>()?.attackPower?.value?:0.0f
            val defence:Float = target.getComponent<AttributeComponent>()?.attackDefense?.value?:0.0f
            damageValue = max(1, (attack-defence).toInt())
            if (isCritical){
                damageValue = (damageValue * 1.5).toInt()
            }
        }

        if (damageSource == DamageSource.Skill){
            if (damageEffect.canCrit){
                var prob = creator.getComponent<AttributeComponent>()?.criticalProbability?.value
                isCritical = Random(100).nextInt()/100f < prob!!
            }
            damageValue = parseDamage()
            if (isCritical){
                damageValue = (damageValue * 1.5).toInt()
            }
        }

        if (damageSource == DamageSource.Buff){
            if (damageEffect.canCrit){
                var prob = creator.getComponent<AttributeComponent>()?.criticalProbability?.value
                isCritical = Random(100).nextInt()/100f < prob!!
            }
            damageValue = parseDamage()
        }
    }

    override fun process() {
        target.receiveDamage(this)

    }

    //后置处理
    override fun postProcess(){
        //触发 造成伤害后 行动点
        creator.triggerActionPoint(ActionPointType.PostCauseDamage, this)
        target.triggerActionPoint(ActionPointType.PostCauseDamage, this)
    }

    enum class DamageSource{
        Attack,//普攻
        Skill,//技能
        Buff,//Buff
    }
}