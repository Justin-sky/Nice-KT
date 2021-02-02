import com.lj.battle.event.EventIdType
import com.lj.core.event.EventSystem
import kt.scaffold.tools.logger.Logger
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.system.measureTimeMillis

class EventSystemTest {

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

        EventSystem.initialize("com.lj.battle.event.handler")
        var time = measureTimeMillis {
            EventSystem.run(EventIdType.changeAttribute,"10001")
            EventSystem.run(EventIdType.changeAttribute,"10001")
        }
        Logger.debug(time.toString())

    }
}