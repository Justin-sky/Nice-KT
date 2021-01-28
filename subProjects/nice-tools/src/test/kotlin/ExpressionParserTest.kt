import kt.scaffold.tools.ExpressionParser
import kt.scaffold.tools.logger.Logger
import org.junit.After
import org.junit.Before
import org.junit.Test

class ExpressionParserTest {
    @Before
    fun setUp(){

    }

    @After
    fun tearDown(){

    }

    @Test
    fun testHealPoint(){

        val parser = ExpressionParser()
        val exp = parser.evaluteExpression("(5+3)*8^2-5*(-2)")
        Logger.debug("${exp.value}")


//        val exp2 = parser.evaluteExpression("sin(x*PI/180)")
//        exp2.parameters["x"]!!.value = 45.0
//        Logger.debug("Sin(45)= ${exp2.value}")
//
//
//        val sinFunc = exp2.toDelegate("x")
//        Logger.debug("Sin(90)= ${sinFunc(arrayOf(90.0))}")
//
//        val exp3 = parser.evaluteExpression("sin(angle/180*PI) * length,cos(angle/180*PI) * length")
//        val f = exp3.toMultiResultDelegate("angle", "length")
//        val res = f(arrayOf(45.0,2.0))
//
//        parser.addFunc("test"){ p->
//            Logger.debug("Test: ${p.size}")
//            return@addFunc 42.0
//        }
//        Logger.debug("Result : ${parser.evaluate("2*test(1,5)")}")
//
//
//        parser.addConst("meaningOfLife"){ 42.0}
//        Logger.debug("Result: ${parser.evaluate("2*meaningOfLife")}")

    }


}