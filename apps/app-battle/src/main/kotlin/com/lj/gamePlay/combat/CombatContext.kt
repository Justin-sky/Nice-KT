package com.lj.gamePlay.combat

import com.lj.core.ecs.Entity

/**
 * 战局上下文
 * 像回合制、moba这种战斗按局来分的，可以创建这个战局上下文，如果是mmo，那么战局上下文应该是在角色进入战斗才会创建，离开战斗就销毁
 */
object CombatContext : Entity() {

    override fun awake() {
        super.awake()

        addComponent<CombatActionManageComponent>()
    }


}