package com.lj.core.gamePlay.config.effect

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("CustomEffect")
class CustomEffect:Effect() {
    var customEffectDescription:String?=null
}