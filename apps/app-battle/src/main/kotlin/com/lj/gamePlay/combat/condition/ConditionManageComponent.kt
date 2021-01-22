package com.lj.gamePlay.combat.condition

import com.lj.core.ecs.Component
import com.lj.core.ecs.Entity

enum class ConditionType(type:Int){
    WhenInTimeNoDamage(0), //当x秒内没有受伤
    WhenHPLower(1),  //当生命值低于x
    WhenHPPctLower(2) //当生命值低于百分比x
}

/**
 * 条件管理组件，在这里管理一个战斗实体所有条件达成事件的添加监听、移除监听、触发流程
 */
class ConditionManageComponent: Component() {

    private val conditions = mutableMapOf<Any, ConditionEntity>()

    override fun setup(){
        super.setup()
    }

    fun addListener(conditionType:ConditionType, action:Any, paramObj:Any? = null){
        when(conditionType){
            ConditionType.WhenInTimeNoDamage ->{
                val time = paramObj as Float

            }
            ConditionType.WhenHPLower ->{

            }
            ConditionType.WhenHPPctLower ->{

            }
        }
    }

    fun removeListener(conditionType: ConditionType, action:Any){
        if (conditions.containsKey(action)){
            conditions[action]?.let { Entity.destroy(it) }
            conditions.remove(action)
        }
    }
}