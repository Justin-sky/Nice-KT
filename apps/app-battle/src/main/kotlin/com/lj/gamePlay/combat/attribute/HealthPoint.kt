package com.lj.gamePlay.combat.attribute

data class HealthPoint(var value:Int = 0, var maxValue:Int = 0) {

    fun reset(){
        this.value = this.maxValue
    }

    fun minus(v:Int){
        this.value = Math.max(0, this.value - v)
    }

    fun add(v:Int){
        this.value = Math.min(this.maxValue, this.value+ v)
    }

    fun percent():Float{
        return (this.value/this.maxValue).toFloat()
    }

    fun percentHealth(pct:Int):Int{
        return (this.maxValue*pct/100)
    }
}