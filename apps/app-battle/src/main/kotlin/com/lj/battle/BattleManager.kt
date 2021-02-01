package com.lj.battle

import com.lj.battle.updater.IUpdater

object BattleManager : IUpdater {

    private val battles = mutableMapOf<Long, Battle>()

    fun getBattle(id:Long): Battle? {
        return battles[id]
    }

    fun addBattel(id:Long, battle: Battle){
        this.battles[id] = battle
    }

    override suspend fun update(){
        battles.values.forEach{item->
            item.update()
        }
    }

}