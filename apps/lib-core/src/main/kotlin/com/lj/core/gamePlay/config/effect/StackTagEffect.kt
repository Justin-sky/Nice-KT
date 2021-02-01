package com.lj.core.gamePlay.config.effect

import com.fasterxml.jackson.annotation.JsonTypeName
import com.lj.core.gamePlay.config.TagType

@JsonTypeName("StackTagEffect")
class StackTagEffect :Effect(){
    var tagType:TagType?=null
    var tagCount:Int = 1
    var tagDuration:Int = 0
}