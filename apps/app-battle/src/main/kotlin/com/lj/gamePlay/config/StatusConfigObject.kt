package com.lj.gamePlay.config

import com.lj.gamePlay.config.effect.Effect

class StatusConfigObject {
    var id:String ?= null
    var name:String ?= null
    var statusType:StatusType ?= null
    var duration:Int = 0
    var showInStatusSlots:Boolean = false
    var canStack:Boolean = false
    var maxStack:Int = 0

    var enableChildrenStatuses:Boolean = false
    var childrenStatuses = mutableListOf<ChildStatus>()

    var enabledStateModify:Boolean = false
    var actionControlType:ActionControlType ?= null

    val enabledAttributeModify:Boolean = false
    var attributeType:AttributeType ?= null
    var numericValue:String ?= null
    var modifyType:ModifyType ?= null

    var enabledLogicTrigger:Boolean = false

    var effects = mutableListOf<Effect>()

}

class ChildStatus{
    var statusConfigObject:StatusConfigObject?=null //状态效果
    var params:MutableMap<String,String>?= mutableMapOf() //参数列表
}

enum class StatusType{
    Buff,
    Debuff,
    Other
}

enum class EffectTriggerType(var p: Int){
    Instant(0),
    Condition(1),
    Action(2),
    Interval(3)
}

enum class ConditionType(var p:Int){
    WhenInTimeNoDamage(0),
    WhenHPLower(1),
    WhenHPPctLower(2)
}