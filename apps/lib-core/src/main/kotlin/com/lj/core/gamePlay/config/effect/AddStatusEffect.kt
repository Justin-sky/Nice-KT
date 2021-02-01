package com.lj.core.gamePlay.config.effect

import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("AddStatusEffect")
class AddStatusEffect:Effect() {
    var addStatus:String?=""
    var duration:Int = 0
    var params = mutableMapOf<String,String>()

}