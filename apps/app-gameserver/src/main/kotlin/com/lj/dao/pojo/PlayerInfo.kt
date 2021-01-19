package com.lj.dao.pojo


data class PlayerInfo(
    var _id:Long = 0,
    var accountInfo: AccountInfo = AccountInfo(),
    var bagInfo:BagInfo = BagInfo()

)