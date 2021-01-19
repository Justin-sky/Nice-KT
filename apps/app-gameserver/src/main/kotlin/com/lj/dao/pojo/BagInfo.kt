package com.lj.dao.pojo

data class Card(
    var _id:Long = 0,
    var name:String = "",
    var star:Int = 1,
    var level: Int = 1
)


data class BagInfo (
    var _id:Long = 0,
    var capacity:Int = 10,
    var cardMap:Map<Long,Card> = mapOf()

)