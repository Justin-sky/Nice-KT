package com.lj.core.ecs

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.lj.core.ecs.entity.PlayerEntity
import io.vertx.core.json.JsonObject

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,property = "@class")
@JsonSubTypes(
    JsonSubTypes.Type(value = PlayerEntity::class, name = "player")
)

open abstract class Entity {
    var _id:Long = 0
    abstract var docName:String  //DB中的Document名称

    val components = mutableMapOf<Any, Component>()

    @JsonIgnore
    val updateComponentJson:JsonObject = JsonObject()

    inline fun <reified T> addComponent(saveDb:Boolean = false):T where T:Component{
        val clz = T::class.java
        var mCreate = clz.getDeclaredConstructor()
        mCreate.isAccessible = true

        val c = mCreate.newInstance()
        c.entity = this
        c.setup()

        this.components[clz.simpleName] = c;

        if (saveDb){
            updateComponentJson.put("components.${clz.simpleName}",JsonObject.mapFrom(c))
        }

        return c
    }

    inline fun <reified T:Component> getComponent(): T? {

        return this.components[T::class.java.simpleName] as T
    }

    inline fun <reified T:Component> removeComponent(){
        this.components.remove(T::class.java.simpleName)
    }



}