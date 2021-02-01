import com.lj.core.ecs.Entity
import com.lj.core.gamePlay.combat.CombatContext
import com.lj.core.gamePlay.combat.entity.CombatEntity
import com.lj.skills.Skill1001Ability
import com.lj.status.StatusScare
import com.lj.core.gamePlay.combat.entity.action.ActionPointType
import com.lj.core.gamePlay.combat.entity.action.DamageAction
import com.lj.core.gamePlay.config.BattleConfigManager
import org.junit.After
import org.junit.Before
import org.junit.Test

class CombatConextTest {
    @Before
    fun setUp(){

    }

    @After
    fun tearDown(){

    }

    @Test
    fun testCombatContext(){

        var context1 = Entity.create<CombatContext>()


        //monster
        val monster = Entity.createWithParent<CombatEntity>(context1)
        monster.listenActionPoint(ActionPointType.PostReceiveDamage){combatAction->

        }
        monster.listenActionPoint(ActionPointType.PostReceiveCure){combatAction->

        }


        val statusConfig = BattleConfigManager.getStatusConfig("Burn")
        val status = monster.attachStatus<StatusScare>(statusConfig!!)
        status.caster = monster
        status.tryActivateAbility()



        //hero
        val hero1 = Entity.createWithParent<CombatEntity>(context1)

        val skillConfig = BattleConfigManager.getSkillConfig("1001")
        val skillAbility = hero1.attachSkill<Skill1001Ability>(skillConfig!!)

        val action = hero1.createCombatAction<DamageAction>()!!
        action.target = monster
        action.damageSource = DamageAction.DamageSource.Attack
        action.applyAction()



    }
}