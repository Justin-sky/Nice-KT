package com.lj.core.gamePlay.config.effect

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("CureEffect")
class CureEffect:Effect() {
    var cureValueFormula:String?=null
}