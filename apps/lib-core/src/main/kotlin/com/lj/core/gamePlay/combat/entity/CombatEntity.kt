package com.lj.core.gamePlay.combat.entity

import com.badlogic.gdx.math.Vector3
import com.lj.core.ecs.Entity
import com.lj.core.gamePlay.combat.CombatActionManageComponent
import com.lj.core.gamePlay.combat.CombatContext
import com.lj.core.gamePlay.combat.entity.ability.AbilityEntity
import com.lj.core.gamePlay.combat.entity.action.*
import com.lj.core.gamePlay.combat.attribute.AttributeComponent
import com.lj.core.gamePlay.combat.attribute.HealthPoint
import com.lj.core.gamePlay.combat.entity.condition.ConditionManageComponent
import com.lj.core.gamePlay.combat.entity.ability.skill.SkillAbility
import com.lj.core.gamePlay.combat.entity.ability.status.StatusAbility
import com.lj.core.gamePlay.config.ActionControlType
import com.lj.core.gamePlay.config.ConditionType

/**
 *  战斗实体
 */
class CombatEntity:Entity() {

    val currentHealth:HealthPoint = HealthPoint()
    val nameSkills = mutableMapOf<String, SkillAbility>()
    val typeIdStatuses = mutableMapOf<String, MutableList<StatusAbility>>()
    val typeStatuses = mutableMapOf<Any, MutableList<StatusAbility>>()

    var position: Vector3 = Vector3()
    var direction:Float = 0f
    lateinit var combatContext:CombatContext
    var actionControlType:ActionControlType = ActionControlType.None

    override fun awake() {
        addComponent<AttributeComponent>()
        addComponent<ActionPointManageComponent>()
        addComponent<ConditionManageComponent>()

        currentHealth.maxValue = getComponent<AttributeComponent>()?.healthPoint?.value?.toInt() ?: 0
        currentHealth.reset()
        combatContext = parent as CombatContext
    }

    inline fun <reified T> createCombatAction(): T? where T:CombatAction{
        return combatContext.getComponent<CombatActionManageComponent>()?.createAction(this)
    }

    //行动点事件
    fun listenActionPoint(actionPointType: ActionPointType, action:(combatAction:CombatAction)->Unit){
        getComponent<ActionPointManageComponent>()?.addListener(actionPointType, action)
    }

    fun unlistenActionPoint(actionPointType: ActionPointType, action:(combatAction:CombatAction)->Unit){
        getComponent<ActionPointManageComponent>()?.removeListener(actionPointType,action)
    }

    fun triggerActionPoint(actionPointType: ActionPointType, action:CombatAction){
        getComponent<ActionPointManageComponent>()?.triggerActionPoint(actionPointType, action)
    }

    //条件事件
    fun listenerCondition(conditionType: ConditionType, action:()->Unit, paramObj: Any? = null){
        getComponent<ConditionManageComponent>()?.addListener(conditionType, action, paramObj)
    }

    fun unlistenCondition(conditionType: ConditionType, action:()->Unit){
        getComponent<ConditionManageComponent>()?.removeListener(conditionType, action)
    }

    fun receiveDamage(combatAction: CombatAction){
        val damageAction = combatAction as DamageAction
        currentHealth.minus(damageAction.damageValue)
    }

    fun receiveCure(combatAction: CombatAction){
        var cureAction = combatAction as CureAction
        currentHealth.add(cureAction.cureValue)
    }

    //挂载能力，技能、被动、buff都通过这个接口挂载
    inline fun <reified T> attachAbility(configObject:Any):T where T: AbilityEntity {
        val ability = Entity.createWithParent<T>(this, configObject)
        ability.onSetParent(this)
        return ability
    }

    inline fun <reified T> attachSkill(configObject: Any) where T:SkillAbility{
        val skill = attachAbility<T>(configObject)
        nameSkills[skill.skillConfigObject.name] = skill
    }

    inline fun <reified T> attachStatus(configObject: Any):T where T:StatusAbility{
        val status = attachAbility<T>(configObject)
        if (!typeIdStatuses.containsKey(status.statusConfigObject.id)){
            typeIdStatuses[status.statusConfigObject.id!!] = mutableListOf<StatusAbility>()
        }
        typeIdStatuses[status.statusConfigObject.id]?.add(status)
        return status
    }

    fun onStatusRemove(statusAbility: StatusAbility){
        typeIdStatuses[statusAbility.statusConfigObject.id]?.remove(statusAbility)
        if (typeIdStatuses[statusAbility.statusConfigObject.id!!].isNullOrEmpty()){
            typeIdStatuses.remove(statusAbility.statusConfigObject.id!!)
        }
        val f = object : RemoveStatusEvent() {
            init {
                combatEntity = this@CombatEntity
                status =statusAbility
                statusId = statusAbility._id
            }
        }
        TODO("这里有问题")
        this.publish(f::class)
    }

    fun <T> hasStatus(statusType:T):Boolean where T:StatusAbility{
        return typeStatuses.containsKey(statusType::class.java)
    }

    fun hasStatus(statusTypeId:String):Boolean{
        return typeIdStatuses.containsKey(statusTypeId)
    }

    fun getStatus(statusTypeId: String): StatusAbility? {
        return typeIdStatuses[statusTypeId]?.get(0)
    }
}

open class RemoveStatusEvent{
    lateinit var combatEntity:CombatEntity
    lateinit var status: StatusAbility
    var statusId:Long = 0

}