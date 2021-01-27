package com.lj.gamePlay.config.effect

import com.fasterxml.jackson.annotation.JsonTypeName
import com.lj.gamePlay.config.StatusConfigObject

@JsonTypeName("RemoveStatusEffect")
class RemoveStatusEffect:Effect() {
    var removeStatus:String?=null
}