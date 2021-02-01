import com.lj.core.gamePlay.combat.attribute.HealthPoint
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class HealthPointTest {

    @Before
    fun setUp(){

    }

    @After
    fun tearDown(){

    }

    @Test
    fun testHealPoint(){
        val  point = HealthPoint(0, 10)
        point.maxValue = 20

        point.add(10)
        point.minus(5)

        assertEquals(5, point.value)

    }


}