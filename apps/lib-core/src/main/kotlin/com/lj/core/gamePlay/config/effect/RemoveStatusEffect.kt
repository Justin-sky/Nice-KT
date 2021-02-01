package com.lj.core.gamePlay.config.effect

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("RemoveStatusEffect")
class RemoveStatusEffect:Effect() {
    var removeStatus:String?=null
}