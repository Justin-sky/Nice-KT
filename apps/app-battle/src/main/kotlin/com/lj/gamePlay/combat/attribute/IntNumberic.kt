package com.lj.gamePlay.combat.attribute

/**
 * 整形修饰器
 */
class IntModifier{
    var value: Int = 0
}

/**
 * 整形修饰器集合
 */
class IntModifierCollection{
    var totalValue:Int = 0

    private val modifiers = mutableListOf<IntModifier>()

    fun addModifier(modifier:IntModifier):Int{
        this.modifiers.add(modifier)
        this.update()
        return this.totalValue
    }

    fun removeModifier(modifier: IntModifier): Int {
        this.modifiers.remove(modifier)
        this.update()
        return this.totalValue
    }

    fun update(){
        this.totalValue = 0
        this.modifiers.forEach { item->
            this.totalValue += item.value
        }
    }
}



class IntNumberic {
    var value:Int = 0
    var baseValue:Int = 0
    var add:Int = 0
    var pctAdd:Int = 0
    var finalAdd:Int = 0
    var finalPctAdd:Int = 0

    private val addCollection = IntModifierCollection()
    private val pctAddCollection = IntModifierCollection()
    private val finalAddCollection = IntModifierCollection()
    private val finalPctAddCollection = IntModifierCollection()

    fun initialize(){
        this.baseValue = 0
        this.add = 0
        this.pctAdd = 0
        this.finalAdd = 0
        this.finalPctAdd = 0
    }

    fun setBase(value:Int):Int{
        this.baseValue = value
        this.update()
        return this.baseValue
    }

    fun addAddModifier(modifier:IntModifier){
        this.add = this.addCollection.addModifier(modifier)
        this.update()
    }

    fun addPctAddModifier(modifier: IntModifier){
        this.pctAdd = this.pctAddCollection.addModifier(modifier)
        this.update()
    }

    fun addFinalAddModifier(modifier: IntModifier){
        this.finalAdd = this.finalAddCollection.addModifier(modifier)
        this.update()
    }

    fun addFinalPctAddModifier(modifier: IntModifier){
        this.finalPctAdd = this.finalPctAddCollection.addModifier(modifier)
        this.update()
    }

    fun removeAddModifier(modifier: IntModifier){
        this.add = this.addCollection.removeModifier(modifier)
        this.update()
    }

    fun removePctAddModifier(modifier: IntModifier){
        this.pctAdd = this.pctAddCollection.removeModifier(modifier)
        this.update()
    }

    fun removeFinalAddModifier(modifier: IntModifier){
        this.finalAdd = this.finalAddCollection.removeModifier(modifier)
        this.update()
    }

    fun removeFinalPctAddModifier(modifier: IntModifier){
        this.finalPctAdd = this.finalPctAddCollection.removeModifier(modifier)
        this.update()
    }

    fun update(){
        val value1 = baseValue
        val value2 = (value1 + add) * (100 + pctAdd) / 100f
        val value3 = (value2 + finalAdd) * (100 + finalPctAdd) / 100f

        this.value = value3.toInt()
    }
}