package com.lj

import kt.scaffold.Application
import kt.scaffold.tools.Number
import kt.scaffold.tools.logger.Logger


class A{
    lateinit var a:String
    lateinit var b:MutableMap<Int,String>
}


fun main() {

    //初始化Vertx
    Application.setupVertx()




    val num = Number(10.0)

    Logger.debug(num.toString())
    Logger.debug(num.value.toString())
}