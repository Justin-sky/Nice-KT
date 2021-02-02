import com.lj.core.ecs.Entity
import com.lj.core.gamePlay.combat.CombatContext
import com.lj.core.gamePlay.combat.entity.CombatEntity
import com.lj.battle.skills.Skill1001Ability
import com.lj.battle.status.StatusScare
import com.lj.core.gamePlay.combat.entity.action.ActionPointType
import com.lj.core.gamePlay.combat.entity.action.DamageAction
import com.lj.core.gamePlay.combat.entity.logic.LogicEntity
import com.lj.core.gamePlay.config.BattleConfigManager
import kt.scaffold.ext.debug
import kt.scaffold.tools.logger.Logger
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.system.measureTimeMillis

class CombatConextTest {
    @Before
    fun setUp(){

        var time = measureTimeMillis {
            //Your code...
        }
    }

    @After
    fun tearDown(){

    }

    @Test
    fun testCombatContext(){


        //战斗上下文  ，自动添加 CombatActionManageComponent 组件
        var context1 = Entity.create<CombatContext>()


        //monster，战斗实体，监听ActionPoint事件
        val monster = Entity.createWithParent<CombatEntity>(context1)
        monster.listenActionPoint(ActionPointType.PostReceiveDamage){combatAction->
            Logger.debug(combatAction.actionType)
        }
        monster.listenActionPoint(ActionPointType.PostReceiveCure){combatAction->
            Logger.debug(combatAction.actionType)
        }

        //给Monster添加状态
        val statusConfig = BattleConfigManager.getStatusConfig("Scare")
        val status = monster.attachStatus<StatusScare>(statusConfig!!)
        status.caster = monster
        status.tryActivateAbility()


        //hero 战斗实体
        val hero1 = Entity.createWithParent<CombatEntity>(context1)

        //添加技能
        val skillConfig = BattleConfigManager.getSkillConfig("1001")
        val skillAbility = hero1.attachSkill<Skill1001Ability>(skillConfig!!)
        skillAbility.applyAbilityEffectsTo(monster)

        val action = hero1.createCombatAction<DamageAction>()!!
        action.target = monster
        action.damageSource = DamageAction.DamageSource.Attack
        action.applyAction()




    }
}