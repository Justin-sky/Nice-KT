package com.lj.gamePlay.config.effect

import com.fasterxml.jackson.annotation.JsonTypeName
import com.lj.gamePlay.config.StatusConfigObject

@JsonTypeName("AddStatusEffect")
class AddStatusEffect:Effect() {
    var addStatus:String?=""
    var duration:Int = 0
    var params = mutableMapOf<String,String>()

}