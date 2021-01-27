package com.lj.gamePlay.config.effect

import com.fasterxml.jackson.annotation.JsonTypeName
import com.lj.gamePlay.config.ShieldType

@JsonTypeName("AddShieldEffect")
class AddShieldEffect:Effect() {

    var shieldType: ShieldType?=null
    var shieldValue:Int = 0
    var shieldDuration:Int = 0
}