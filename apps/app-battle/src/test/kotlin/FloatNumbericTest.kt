import com.lj.gamePlay.combat.attribute.FloatModifier
import com.lj.gamePlay.combat.attribute.FloatNumberic
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FloatNumbericTest {

    lateinit var floatNumber: FloatNumberic

    @Before
    fun setUp(){

        floatNumber = FloatNumberic()
        floatNumber.initialize()
    }

    @After
    fun tearDown(){

    }

    @Test
    fun testSetBase(){
        val baseV = floatNumber.setBase(10f)
        assertTrue("testSetBase") {
            baseV == 10f
        }
    }

    @Test
    fun testAdd(){
        // 10 + 10
        floatNumber.setBase(10f)
        val modifier = FloatModifier(10f)

        floatNumber.addAddModifier(modifier)
        assertEquals(20f, floatNumber.value, "add")

        floatNumber.removeAddModifier(modifier)
        assertEquals(10f, floatNumber.value, "add")
    }

    @Test
    fun testAddPct(){
        //(10 + 10) *  (100+10)/100
        floatNumber.setBase(10f)
        val addModifier = FloatModifier(10f)
        val pctModifier = FloatModifier(10f)
        floatNumber.addAddModifier(addModifier)
        floatNumber.addPctAddModifier(pctModifier)

        assertEquals(22f,floatNumber.value,"addpct")

        floatNumber.removeAddModifier(addModifier)
        floatNumber.removePctAddModifier(pctModifier)

        assertEquals(10f,floatNumber.value,"addpct")

    }

    @Test
    fun testAddFinal(){
        //( ( (10 + 10) * (100 +10)/100)  +  10 ) * (100 + 10)/100
        floatNumber.setBase(10f)
        val addModifier = FloatModifier(10f)
        val pctModifier = FloatModifier(10f)
        val addFinalModifier = FloatModifier(10f)
        val addFinalPctModifier = FloatModifier(10f)

        floatNumber.addAddModifier(addModifier)
        floatNumber.addPctAddModifier(pctModifier)
        floatNumber.addFinalAddModifier(addFinalModifier)
        floatNumber.addFinalPctAddModifier(addFinalPctModifier)

        assertEquals(35.2f, floatNumber.value, "addfinal")

        floatNumber.removeAddModifier(addModifier)
        floatNumber.removePctAddModifier(pctModifier)
        floatNumber.removeFinalAddModifier(addFinalModifier)
        floatNumber.removeFinalPctAddModifier(addFinalPctModifier)

        assertEquals(10f,floatNumber.value,"addfinal")
    }
}