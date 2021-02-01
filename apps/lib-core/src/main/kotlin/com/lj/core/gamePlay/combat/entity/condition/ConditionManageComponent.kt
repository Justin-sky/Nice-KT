package com.lj.core.gamePlay.combat.entity.condition

import com.lj.core.ecs.Component
import com.lj.core.ecs.Entity
import com.lj.core.gamePlay.config.ConditionType

/**
 * 条件管理组件，在这里管理一个战斗实体所有条件达成事件的添加监听、移除监听、触发流程
 */
class ConditionManageComponent: Component() {

    private val conditions = mutableMapOf<()->Unit, ConditionEntity>()

    override fun setup(){
        super.setup()
    }

    fun addListener(conditionType: ConditionType, action:()->Unit, paramObj:Any? = null){
        when(conditionType){
            ConditionType.WhenInTimeNoDamage ->{
                val time = paramObj as Float
                val condition = Entity.createWithParent<WhenInTimeNoDamageCondition>(entity, time)
                conditions[action] = condition
            }
            ConditionType.WhenHPLower ->{

            }
            ConditionType.WhenHPPctLower ->{

            }else ->{

             }
        }
    }

    fun removeListener(conditionType: ConditionType, action:()->Unit){
        if (conditions.containsKey(action)){
            conditions[action]?.let { Entity.destroy(it) }
            conditions.remove(action)
        }
    }
}