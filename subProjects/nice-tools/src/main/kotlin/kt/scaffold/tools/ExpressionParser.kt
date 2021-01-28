package kt.scaffold.tools

import java.util.*
import kotlin.math.*

/**
 * Convert From
 * http://wiki.unity3d.com/index.php/ExpressionParser#Examples_.28CSharp.29
 */
interface IValue{
    val value:Double
}

open class Number(override var value: Double) :IValue{
    override fun toString(): String {
        return "$value"
    }
}

class OperationSum(aValues: Array<IValue>) : IValue{
    private var mValues:Array<IValue>
    override val value:Double
        get() {
            val sum = mValues.sumByDouble { it.value }
            return sum
        }

    init {
        val v = mutableListOf<IValue>()
        for (i in aValues){
            val sum = i as? OperationSum
            if (sum == null) {
                v.add(i)
            }else{
                v.addAll(sum.mValues)
            }
        }
        mValues = v.toTypedArray()
    }

    override fun toString(): String {
        return "(${mValues.joinToString(separator = "+")})"
    }
}

class OperationProduct(private var m_values: Array<IValue>):IValue{
    override val value:Double
        get() {
            val product =  m_values.fold(1.0){
                    acc,i:IValue->
                  acc * i.value
            }
            return product
        }

    override fun toString(): String {
        return "(${m_values.joinToString(separator = "*")})"
    }
}

class OperationPower(var m_value: IValue, var m_power: IValue) : IValue {
    override val value:Double
        get() {
            val power = m_value.value.pow(m_power.value)
            return power
        }

    override fun toString(): String {
        return "( $m_value^$m_power )"
    }
}

class OperationNegate(var m_value:IValue):IValue{
    override val value:Double
        get() {
            return -m_value.value
        }

    override fun toString(): String {
        return "( -$m_value )"
    }
}

class OperationReciprocal(var m_value:IValue):IValue{
    override val value:Double
        get() {
            return 1.0/m_value.value
        }

    override fun toString(): String {
        return "( 1/ $m_value )"
    }
}

class MultiParameterList(var m_values:Array<IValue>):IValue{
    val parameters:Array<IValue>
        get() = m_values

    override val value:Double
        get() {
            return m_values[0].value
        }

    override fun toString(): String {
        return m_values.joinToString(separator = ",")
    }
}

class CustomFunction(var m_name:String, var m_deletage: (Array<Double>) -> Double, var m_params:Array<IValue>):IValue{
    override val value:Double
        get() {
            if (m_params.isNullOrEmpty()){
                return m_deletage(arrayOf())
            }else{
                return m_deletage(m_params.map{it -> it.value}.toTypedArray())
            }
        }

    override fun toString(): String {
        return "( ${m_params.joinToString(separator = ",")} )"
    }
}

class Parameter(var name:String): Number(0.0) {
    override fun toString(): String {
        return "$name[${super.toString()}]"
    }
}

class Expression:IValue{
    val parameters = mutableMapOf<String, Parameter>()
    var expressionTree:IValue = Number(0.0)

    override val value: Double
        get() = expressionTree.value

    val multiValue: Array<Double>
        get() {
            val t = expressionTree as? MultiParameterList
            if (t != null){
                return t.parameters.map { i->i.value }.toTypedArray()
            }
            return arrayOf()
        }

    override fun toString(): String {
        return expressionTree.toString()
    }

    fun toDelegate(vararg aParamOrder:String):(aParams:Array<Double>)->Double{
        val param = mutableListOf<Parameter?>()
        for(aParam in aParamOrder){
            param.add(parameters[aParam])
        }
        val param2 = param.toTypedArray()

        return { p -> invoke(p, param2)}
    }

    fun toMultiResultDelegate(vararg aParamOrder:String):(aParams:Array<Double>)->Array<Double>{
        val param = mutableListOf<Parameter?>()
        for(aParam in aParamOrder){
            param.add(parameters[aParam])
        }
        val param2 = param.toTypedArray()

        return { p -> invokeMultiResult(p, param2)}
    }

    fun invoke(aParams:Array<Double>, aParamList:Array<Parameter?>):Double{
        val count = min(aParamList.size, aParams.size)
        for (i in 0 until count){
            aParamList[i]?.value = aParams[i]
        }
        return value
    }

    fun invokeMultiResult(aParams:Array<Double>, aParamList:Array<Parameter?>):Array<Double>{
        val count = min(aParamList.size, aParams.size)
        for (i in 0 until count){
            aParamList[i]?.value = aParams[i]
        }
        return multiValue
    }

    companion object{
        fun parse(aExpression:String):Expression{
            return ExpressionParser().evaluteExpression(aExpression)
        }
    }

    class ParameterExpression(aMessage:String): Exception(aMessage)
}


class ExpressionParser {
    private val m_BracketHeap = mutableListOf<String>()
    private val m_consts = mutableMapOf<String, ()->Double>()
    private val m_funcs = mutableMapOf<String,(Array<Double>)->Double>()
    private var m_context:Expression?=null

    init {
        val rnd = Random()
        m_consts["PI"] = { Math.PI }
        m_consts["e"] = {Math.E }
        m_funcs["sqrt"] = { p -> sqrt(p.first()) }
        m_funcs["abs"] = { p -> abs(p.first()) }
        m_funcs["ln"] = { p -> ln(p.first()) }
        m_funcs["floor"] = { p -> floor(p.first()) }
        m_funcs["ceiling"] = { p-> ceil(p.first()) }
        m_funcs["round"] = { p -> p.first().roundToLong().toDouble() }
        m_funcs["sin"] = { p -> sin(p.first()) }
        m_funcs["cos"] = { p -> cos(p.first()) }
        m_funcs["tan"] = { p -> tan(p.first()) }
        m_funcs["asin"] = { p -> asin(p.first()) }
        m_funcs["acos"] = { p -> acos(p.first()) }
        m_funcs["atan"] = { p -> atan(p.first()) }
        m_funcs["atan2"] = { p -> atan2(p.first(), p[1]) }
        m_funcs["min"] = { p -> min(p.first(), p[1]) }
        m_funcs["max"] = { p -> max(p.first(), p[1]) }
        m_funcs["rnd"] = { p ->
            when(p.size){
                1 -> rnd.nextDouble() * p.first()
                2 -> p.first() + rnd.nextDouble() * (p[1] - p.first())
                else -> rnd.nextDouble()
            }
        }
    }

    fun addFunc(aName:String, aMethod:(Array<Double>)->Double){
        m_funcs[aName] = aMethod
    }

    fun addConst(aName: String, aMethod:()->Double){
        m_consts[aName] = aMethod
    }

    fun removeFunc(aName:String){
        if(m_funcs.containsKey(aName)) m_funcs.remove(aName)
    }

    fun removeConst(aName:String){
        if(m_consts.containsKey(aName)) m_consts.remove(aName)
    }

    fun findClosingBracket(aText:String, aStart:Int, aOpen:Char, aClose:Char):Int{
        var counter = 0
        for (i in aStart until aText.length){
            if(aText[i] == aOpen) counter++
            if (aText[i] == aClose) counter--
            if (counter == 0) return i
        }
        return -1
    }

    fun substitudeBracket(aExpression:String, aIndex:Int):String{
        val closing = findClosingBracket(aExpression, aIndex, '(', ')')
        if (closing > aIndex + 1){
            val inner = aExpression.substring(aIndex+1, closing)
            m_BracketHeap.add(inner)
            val sub = "&${m_BracketHeap.size - 1};"
            return aExpression.substring(0, aIndex) + sub + aExpression.substring(closing+1)
        }else{
            throw ParseException("Bracket not closed!")
        }
    }

    fun parse(aExp:String):IValue{
        var aExpression = aExp.trim()
        var index = aExpression.indexOf('(')
        while (index >= 0){
            aExpression = substitudeBracket(aExpression, index)
            index = aExpression.indexOf("(")
        }
        if (aExpression.contains(',')){
            val parts = aExpression.split(',')
            val exp = mutableListOf<IValue>()
            for (part in parts){
                val s = part.trim()
                if (!s.isNullOrEmpty()) exp.add(parse(s))
            }
            return MultiParameterList(exp.toTypedArray())

        }else if (aExpression.contains('+')){
            val parts = aExpression.split('+')
            val exp = mutableListOf<IValue>()
            for (part in parts){
                val s = part.trim()
                if (!s.isNullOrEmpty()) exp.add(parse(s))
            }
            if (exp.size == 1) return exp[0]
            return OperationSum(exp.toTypedArray())

        }else if (aExpression.contains('-')){
            val parts = aExpression.split('-')
            val exp = mutableListOf<IValue>()
            if (!parts[0].trim().isNullOrEmpty()) exp.add(parse(parts[0]))
            for (i in 1 until parts.size){
                val s = parts[i].trim()
                if (!s.isNullOrEmpty()) exp.add(OperationNegate(parse(s)))
            }
            if (exp.size == 1) return exp[0]
            return OperationSum(exp.toTypedArray())

        }else if (aExpression.contains('*')){
            val parts = aExpression.split('*')
            val exp = mutableListOf<IValue>()
            for (part in parts){
                exp.add(parse(part))
            }
            if (exp.size == 1) return exp[0]
            return OperationProduct(exp.toTypedArray())

        }else if (aExpression.contains('/')){
            val parts = aExpression.split('/')
            val exp = mutableListOf<IValue>()
            if (!parts[0].trim().isNullOrEmpty()) exp.add(parse(parts[0]))
            for (i in 1 until parts.size){
                val s = parts[i].trim()
                if (!s.isNullOrEmpty()) exp.add(OperationReciprocal(parse(s)))
            }
            return OperationProduct(exp.toTypedArray())

        }else if(aExpression.contains('^')){
            val pos = aExpression.indexOf('^')
            val value = parse(aExpression.substring(0, pos))
            val pow = parse(aExpression.substring(pos + 1))
            return OperationPower(value, pow)
        }
        val mPos = aExpression.indexOf("&")
        if (mPos > 0){
            val fName = aExpression.substring(0, mPos)
            val func = m_funcs[fName]
            if (func != null){
                val inner = aExpression.substring(fName.length)
                val param = parse(inner)
                val multiParams = param as? MultiParameterList
                val parameters:Array<IValue>
                if (multiParams != null) {
                    parameters = multiParams.parameters
                }else{
                    parameters = arrayOf(param)
                }
                return CustomFunction(fName, func, parameters)
            }
        }

        val constF = m_consts[aExpression]
        if (constF != null){
            //参数为空数组，所以输出的表达式 在 常量处为 空
            return CustomFunction(aExpression,{ constF() }, arrayOf())
        }

        val index2a = aExpression.indexOf('&')
        val index2b = aExpression.indexOf(';')
        if (index2a >= 0 && index2b >= 2){
            val inner = aExpression.substring(index2a + 1, index2b)
            val bracketIndex = inner.toIntOrNull()
            if (bracketIndex !=null && bracketIndex >= 0 && bracketIndex < m_BracketHeap.size){
                return parse(m_BracketHeap[bracketIndex])
            }else{
                throw ParseException("Can't parse substitude token")
            }
        }
        val doubleValue = aExpression.toDoubleOrNull()
        if (doubleValue != null) return Number(doubleValue)
        if (validIdentifier(aExpression)){
            if (m_context!!.parameters.containsKey(aExpression))
                return m_context!!.parameters[aExpression]!!
            val value = Parameter(aExpression)
            m_context!!.parameters.put(aExpression, value)
            return value
        }
        throw ParseException("Reached unexpected end within the parsing tree")
    }

    private fun validIdentifier(aExpression: String):Boolean{
        val exp = aExpression.trim()
        if (exp.isNullOrEmpty()) return false
        if (exp.contains(" ")) return false
        if (m_consts.containsKey(exp)) return false
        if (m_funcs.containsKey(exp)) return false

        return true
    }

    fun evaluteExpression(aExpression: String):Expression{
        val expression = Expression()
        m_context = expression
        expression.expressionTree = parse(aExpression)
        m_context = null
        m_BracketHeap.clear()
        return expression
    }

    fun evaluate(aExpression: String):Double{
        return evaluteExpression(aExpression).value
    }

    companion object{
        fun eval(aExpression: String):Double{
            return ExpressionParser().evaluate(aExpression)
        }
    }

    class ParseException(aMessage:String):Exception(aMessage)
}