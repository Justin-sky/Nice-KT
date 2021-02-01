package com.lj.core.gamePlay.combat.entity.ability

/**
 * 能力单元体
 */
class AbilityItem:AbilityEntity() {

    lateinit var unitInitData:Any

    override fun awake(initData:Any) {
        this.unitInitData = initData
    }

}