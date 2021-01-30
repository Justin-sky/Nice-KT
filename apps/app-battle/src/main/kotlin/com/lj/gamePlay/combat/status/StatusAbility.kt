package com.lj.gamePlay.combat.status

import com.lj.core.ecs.Entity
import com.lj.gamePlay.combat.ability.AbilityEntity
import com.lj.gamePlay.combat.attribute.AttributeComponent
import com.lj.gamePlay.combat.attribute.FloatModifier
import com.lj.gamePlay.combat.entity.CombatEntity
import com.lj.gamePlay.combat.status.logic.LogicActionTriggerComponent
import com.lj.gamePlay.combat.status.logic.LogicConditionTriggerComponent
import com.lj.gamePlay.combat.status.logic.LogicEntity
import com.lj.gamePlay.combat.status.logic.LogicIntervalTriggerComponent
import com.lj.gamePlay.config.*
import com.lj.gamePlay.helper.ExpressionHelper

class StatusAbility :AbilityEntity(){

    var caster:CombatEntity?=null
    lateinit var statusConfigObject:StatusConfigObject
    var numericModifier:FloatModifier?= null
    var isChildStatus:Boolean = false
    var childStatusData:ChildStatus?=null
    private var childrenStatuses = mutableListOf<StatusAbility>()

    override fun awake(initData: Any) {
        super.awake(initData)
        statusConfigObject = initData as StatusConfigObject
        name = statusConfigObject.id
    }

    //激活
    override fun activateAbility() {
        super.activateAbility()

        //子状态效果
        if (statusConfigObject.enableChildrenStatuses){
            for (item in statusConfigObject.childrenStatuses){
                val status = ownerEntity.attachStatus<StatusAbility>(item.statusConfigObject!!)
                status.caster = ownerEntity
                status.isChildStatus = true
                status.childStatusData = item

                status.tryActivateAbility()

                childrenStatuses.add(status)

            }
        }
        ////行为禁制
        if (statusConfigObject.enabledAttributeModify){
            if (statusConfigObject.attributeType != AttributeType.None && statusConfigObject.numericValue != ""){
                var numericValue = statusConfigObject.numericValue
                if (isChildStatus){
                    childStatusData?.params?.forEach { item ->
                        numericValue = numericValue?.replace(item.key, item.value)
                    }
                }
                numericValue = numericValue?.replace("%","")
                val expression = ExpressionHelper.expressionParser.evaluteExpression(numericValue!!)
                val value = expression.value.toFloat()
                numericModifier = FloatModifier(value)

                val attributeType = statusConfigObject.attributeType?.name
                if (statusConfigObject.modifyType == ModifyType.Add){
                    getParentT<CombatEntity>().getComponent<AttributeComponent>()?.getNumeric(attributeType!!)?.addFinalAddModifier(numericModifier!!)
                }
                if (statusConfigObject.modifyType == ModifyType.PercentAdd){
                    getParentT<CombatEntity>().getComponent<AttributeComponent>()?.getNumeric(attributeType!!)?.addFinalPctAddModifier(numericModifier!!)
                }
            }
        }
        //逻辑触发
        if (statusConfigObject.enabledLogicTrigger){
            statusConfigObject.effects.forEach { item->
                val logicEntity = Entity.createWithParent<LogicEntity>(this, item)
                if (item.effectTriggerType == EffectTriggerType.Instant){
                    logicEntity.applyEffect()
                    destroy(logicEntity)
                }
                if (item.effectTriggerType == EffectTriggerType.Interval){
                    logicEntity.addComponent<LogicIntervalTriggerComponent>()
                }
                if (item.effectTriggerType == EffectTriggerType.Condition){
                    logicEntity.addComponent<LogicConditionTriggerComponent>()
                }
                if (item.effectTriggerType == EffectTriggerType.Action){
                    logicEntity.addComponent<LogicActionTriggerComponent>()
                }
            }
        }

    }

    override fun endAbility() {
        //子状态效果
        if (statusConfigObject.enableChildrenStatuses){
            childrenStatuses.forEach { item->
                item.endAbility()
            }
            childrenStatuses.clear()
        }
        //行为禁制
        if (statusConfigObject.enabledStateModify){

            val v = ownerEntity.actionControlType.toValue() or statusConfigObject.actionControlType!!.toValue().inv()
            ownerEntity.actionControlType.code = v
        }
        //属性修饰
        if (statusConfigObject.enabledAttributeModify){
            if (statusConfigObject.attributeType != AttributeType.None && statusConfigObject.numericValue != ""){
                val attributeType = statusConfigObject.attributeType?.name!!
                if (statusConfigObject.modifyType == ModifyType.Add){
                    getParentT<CombatEntity>().
                    getComponent<AttributeComponent>()?.
                    getNumeric(attributeType)?.
                    removeFinalAddModifier(numericModifier!!)
                }
                if (statusConfigObject.modifyType == ModifyType.PercentAdd){
                    getParentT<CombatEntity>().
                    getComponent<AttributeComponent>()?.
                    getNumeric(attributeType)?.
                    removeFinalPctAddModifier(numericModifier!!)
                }
            }
        }
        //逻辑触发
        if (statusConfigObject.enabledLogicTrigger){

        }
        numericModifier = null
        getParentT<CombatEntity>().onStatusRemove(this)
        super.endAbility()
    }

    override fun applyAbilityEffectsTo(targetEntity: CombatEntity) {
        super.applyAbilityEffectsTo(targetEntity)
    }

}