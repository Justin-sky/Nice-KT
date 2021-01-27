package com.lj.gamePlay.config.effect

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.lj.core.ecs.entity.PlayerEntity
import com.lj.gamePlay.combat.action.ActionPointType
import com.lj.gamePlay.config.*


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,property = "@class")
@JsonSubTypes(
    JsonSubTypes.Type(value = AddShieldEffect::class, name = "AddShieldEffect"),
    JsonSubTypes.Type(value = AddStatusEffect::class, name = "AddStatusEffect"),
    JsonSubTypes.Type(value = AttributeNumericModifyEffect::class, name = "AttributeNumericModifyEffect"),
    JsonSubTypes.Type(value = ClearAllStatusEffect::class, name = "ClearAllStatusEffect"),
    JsonSubTypes.Type(value = CureEffect::class, name = "CureEffect"),
    JsonSubTypes.Type(value = CustomEffect::class, name = "CustomEffect"),
    JsonSubTypes.Type(value = DamageEffect::class, name = "DamageEffect"),
    JsonSubTypes.Type(value = RemoveStatusEffect::class, name = "RemoveStatusEffect"),
    JsonSubTypes.Type(value = StackTagEffect::class, name = "StackTagEffect")
)
abstract class Effect {

    var isSkillEffect:Boolean = true
    var addSkillEffectTargetType: AddSkillEffetTargetType?=null
    var effectTriggerType: EffectTriggerType?=null
    var conditionType:ConditionType?=null
    var actionPointType: ActionPointType?=null
    var interval:String?=null
    var conditionParam:String?=null
    var triggerProbability:String?=null
}