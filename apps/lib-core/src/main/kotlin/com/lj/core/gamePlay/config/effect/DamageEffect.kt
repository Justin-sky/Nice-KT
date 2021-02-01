package com.lj.core.gamePlay.config.effect

import com.fasterxml.jackson.annotation.JsonTypeName
import com.lj.core.gamePlay.config.DamageType

@JsonTypeName("DamageEffect")
class DamageEffect:Effect() {
    var damageType: DamageType?=null
    var damageValueFormula:String?=null
    var canCrit:Boolean = false
}