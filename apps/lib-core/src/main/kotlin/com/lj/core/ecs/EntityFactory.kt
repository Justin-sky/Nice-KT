package com.lj.core.ecs

object EntityFactory {


    inline fun <reified T> create(id:Long):T where T:Entity{

        val clz = T::class.java
        var mCreate = clz.getDeclaredConstructor()
        mCreate.isAccessible = true

        val c = mCreate.newInstance()
        c._id = id

        return  c
    }

}