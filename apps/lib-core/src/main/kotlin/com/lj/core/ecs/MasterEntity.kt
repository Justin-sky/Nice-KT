package com.lj.core.ecs

object MasterEntity :Entity(){

    val entities = mutableMapOf<Any, MutableList<Entity>>()

    val allComponents = mutableListOf<Component>()

    fun update(){

        if (allComponents.size == 0 ) return

        for (i in allComponents.size-1 downTo 0){
            val it = allComponents[i]
            if(it.isDisposed){
                allComponents.remove(it)
                continue
            }

            if (it.disable) continue

            it.update()
        }

    }
}
