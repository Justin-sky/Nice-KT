package com.lj

import com.lj.gamePlay.config.BattleConfigManager
import com.lj.gamePlay.config.BattleConfigType
import kt.scaffold.Application
import kt.scaffold.tools.logger.Logger


class A{
    lateinit var a:String
    lateinit var b:MutableMap<Int,String>
}


fun main() {

    //初始化Vertx
    Application.setupVertx()





    //val a =   BattleConfigManager.getSkillConfig("1004")

    val b = BattleConfigManager.getStatusConfig("Weak")

    Logger.debug(b.toString())



}