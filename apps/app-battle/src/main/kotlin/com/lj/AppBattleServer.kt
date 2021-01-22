package com.lj

import kt.scaffold.tools.logger.Logger

fun main() {

    val list = mutableListOf<Int>(1,2,3,4)

    for (i in list.size-1 downTo 0){

        val it = list[i]
        Logger.debug("hhhhhhhhhhhhhh   $it")
    }

    Logger.debug("Hello")
}