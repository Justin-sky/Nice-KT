import com.lj.gamePlay.combat.attribute.FloatNumberic
import com.lj.gamePlay.config.BattleConfigManager
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class BattleConfigTest {

    @Before
    fun setUp(){
    }

    @After
    fun tearDown(){
    }

    @Test
    fun testSetBase(){
        BattleConfigManager.getSkillConfig("1001")
        BattleConfigManager.getSkillConfig("1002")
        BattleConfigManager.getSkillConfig("1003")
        BattleConfigManager.getSkillConfig("1004")

        BattleConfigManager.getStatusConfig("AttackPowerModify")
        BattleConfigManager.getStatusConfig("AttackPowerPctModify")
        BattleConfigManager.getStatusConfig("Burn")
        BattleConfigManager.getStatusConfig("CauseDamagePctModify")
        BattleConfigManager.getStatusConfig("CauseDamagePctModify")
        BattleConfigManager.getStatusConfig("HellAngel")
        BattleConfigManager.getStatusConfig("MoveSpeedPctModify")
        BattleConfigManager.getStatusConfig("Mute")
        BattleConfigManager.getStatusConfig("Scare")
        BattleConfigManager.getStatusConfig("Shield")
        BattleConfigManager.getStatusConfig("Vertigo")
        BattleConfigManager.getStatusConfig("Weak")
    }
}