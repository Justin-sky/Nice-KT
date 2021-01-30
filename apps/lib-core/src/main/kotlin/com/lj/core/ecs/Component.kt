package com.lj.core.ecs

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.lj.core.ecs.component.AccountComponent
import com.lj.core.ecs.component.BagComponent
import com.lj.core.ecs.component.TeamComponent
import com.lj.core.ecs.entity.PlayerEntity
import io.vertx.core.json.JsonObject

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,property = "@class")
@JsonSubTypes(
    JsonSubTypes.Type(value = AccountComponent::class, name = "account"),
    JsonSubTypes.Type(value = BagComponent::class, name = "bag"),
    JsonSubTypes.Type(value = TeamComponent::class, name = "team")
)
open abstract class Component {
    var _id:Long = 1

    @JsonIgnore
    open var enable:Boolean = true

    val disable:Boolean
        @JsonIgnore
        get() {
            return !enable
        }


    var isDisposed:Boolean = false
        @JsonIgnore
        get

    @JsonBackReference  //加此此注解。。不会被序列化，解决循环依赖问题
    lateinit var entity:Entity

    @JsonIgnore
    fun <T> getEntityT():T where T:Entity{
        return entity as T
    }

    private val updateJson:JsonObject = JsonObject() //更新cache
    private val unsetJson:JsonObject = JsonObject() //删除键
    private val pushJson:JsonObject= JsonObject()  //数组中添加对象
    private val pullJson:JsonObject = JsonObject() //删除数组对象


    fun addUpdateJson(key:String, value:Any){
        this.updateJson.put(key,value)
    }

    fun getUpdateJson(clear:Boolean = true):JsonObject{
        val json = JsonObject()
        updateJson.forEach(){ entity->
            json.put(entity.key, entity.value)
        }
        if (clear) this.updateJson.clear()

        return json;
    }

    fun addUnsetJson(key: String, value: Any){
        this.unsetJson.put(key, value)
    }

    fun getUnsetJson(clear: Boolean = true):JsonObject{
        val json = JsonObject()
        unsetJson.forEach(){ entity->
            json.put(entity.key, entity.value)
        }
        if (clear) this.unsetJson.clear()

        return json;
    }

    fun addPushJson(key:String, value: Any){
        this.pushJson.put(key,value)
    }

    fun getPushJson(clear:Boolean = true):JsonObject{
        val json = JsonObject()
        pushJson.forEach(){ entity->
            json.put(entity.key, entity.value)
        }
        if (clear) this.pushJson.clear()

        return json;
    }

    fun addPullJson(key: String, value: Any){
        this.pullJson.put(key,value)
    }

    fun getPullJson(clear:Boolean = true):JsonObject{
        val json = JsonObject()
        pullJson.forEach(){ entity->
            json.put(entity.key, entity.value)
        }
        if (clear) this.pullJson.clear()

        return json;
    }


    open fun setup(){}

    open fun setup(initData:Any){}

    open fun update(){}

    open fun onDestroy(){}

    open fun dispose(){
        isDisposed = true
    }

}