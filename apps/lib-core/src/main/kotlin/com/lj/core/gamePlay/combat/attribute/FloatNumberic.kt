package com.lj.core.gamePlay.combat.attribute

/**
 * 浮点型修饰器
 */
class FloatModifier(var value: Float = 0.0f)

/**
 * 浮点型修饰器集合
 */
class FloatModifierCollection{
    var totalValue:Float = 0.0f

    private val modifiers = mutableListOf<FloatModifier>()

    fun addModifier(modifier:FloatModifier):Float{
        this.modifiers.add(modifier)
        this.update()
        return this.totalValue
    }

    fun removeModifier(modifier: FloatModifier): Float {
        this.modifiers.remove(modifier)
        this.update()
        return this.totalValue
    }

    fun update(){
        this.totalValue = 0.0f
        this.modifiers.forEach { item->
            this.totalValue += item.value
        }
    }
}



class FloatNumberic {
    var value:Float = 0.0f
    var baseValue:Float = 0.0f
    var add:Float = 0.0f
    var pctAdd:Float = 0.0f
    var finalAdd:Float = 0.0f
    var finalPctAdd:Float = 0.0f

    private val addCollection = FloatModifierCollection()
    private val pctAddCollection = FloatModifierCollection()
    private val finalAddCollection = FloatModifierCollection()
    private val finalPctAddCollection = FloatModifierCollection()

    fun initialize(){
        this.baseValue = 0f
        this.add = 0f
        this.pctAdd = 0f
        this.finalAdd = 0f
        this.finalPctAdd = 0f
    }

    fun setBase(value:Float):Float{
        this.baseValue = value
        this.update()
        return this.baseValue
    }

    fun addAddModifier(modifier:FloatModifier){
        this.add = this.addCollection.addModifier(modifier)
        this.update()
    }

    fun addPctAddModifier(modifier: FloatModifier){
        this.pctAdd = this.pctAddCollection.addModifier(modifier)
        this.update()
    }

    fun addFinalAddModifier(modifier: FloatModifier){
        this.finalAdd = this.finalAddCollection.addModifier(modifier)
        this.update()
    }

    fun addFinalPctAddModifier(modifier: FloatModifier){
        this.finalPctAdd = this.finalPctAddCollection.addModifier(modifier)
        this.update()
    }

    fun removeAddModifier(modifier: FloatModifier){
        this.add = this.addCollection.removeModifier(modifier)
        this.update()
    }

    fun removePctAddModifier(modifier: FloatModifier){
        this.pctAdd = this.pctAddCollection.removeModifier(modifier)
        this.update()
    }

    fun removeFinalAddModifier(modifier: FloatModifier){
        this.finalAdd = this.finalAddCollection.removeModifier(modifier)
        this.update()
    }

    fun removeFinalPctAddModifier(modifier: FloatModifier){
        this.finalPctAdd = this.finalPctAddCollection.removeModifier(modifier)
        this.update()
    }

    fun update(){
        val value1 = baseValue
        val value2 = (value1 + add) * (100 + pctAdd) / 100f
        val value3 = (value2 + finalAdd) * (100 + finalPctAdd) / 100f

        this.value = value3
    }
}