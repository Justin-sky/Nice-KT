package com.lj.gamePlay.config.effect

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("CustomEffect")
class CustomEffect:Effect() {
    var customEffectDescription:String?=null
}