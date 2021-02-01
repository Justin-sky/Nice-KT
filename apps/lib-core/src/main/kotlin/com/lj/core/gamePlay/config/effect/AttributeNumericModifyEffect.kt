package com.lj.core.gamePlay.config.effect

import com.fasterxml.jackson.annotation.JsonTypeName
import com.lj.core.gamePlay.combat.attribute.AttributeType

@JsonTypeName("AttributeNumericModifyEffect")
class AttributeNumericModifyEffect:Effect() {

    var numericType:AttributeType?=null
    var numericValue:String?=null
}