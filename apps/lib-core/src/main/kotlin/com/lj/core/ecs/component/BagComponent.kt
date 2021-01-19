package com.lj.core.ecs.component

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeName
import com.lj.core.ecs.Component
import io.vertx.core.json.JsonObject

data class Card(
    var _id:Long = 0,
    var name:String = "",
    var star:Int = 1,
    var level: Int = 1
)

@JsonTypeName("bag")
class BagComponent: Component() {

    var capacity:Int = 10
    val cardList:MutableList<Card> = mutableListOf()


    fun changeCapacity(capacity:Int) {
        this.capacity = capacity
        this.addUpdateJson("components.BagComponent.capacity",capacity)
    }

    fun addCard(card: Card){
        this.cardList.add(card)
        this.addPushJson("components.BagComponent.cardList",JsonObject.mapFrom(card))
    }

    fun removeCard(card: Card){
        this.cardList.remove(card)
        this.addPullJson("components.BagComponent.cardList",JsonObject().put("_id",card._id))
    }

    @JsonIgnore
    fun copyAllCards():List<Card>{
        return this.cardList.toList()
    }
}