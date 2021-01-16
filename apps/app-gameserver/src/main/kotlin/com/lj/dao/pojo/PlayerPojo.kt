package com.lj.dao.pojo

data class PlayerPojo(
    var id:Long = 0,
    var name:String = "",
    var bag:BagPojo? = null
)


data class BagPojo(
    var id:Int = 0,
    var size:Int = 0,
    var items:List<BagItemPojo>?= null
)


data class BagItemPojo(
    var id:Int = 0,
    var name:String = ""
)