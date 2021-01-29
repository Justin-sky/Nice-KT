package com.lj.core.ecs

object IdFactory {

    private var baseTicks:Long = 0

    fun newInstanceId():Long{
        return ++baseTicks
    }
}