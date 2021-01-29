package com.lj.core.ecs

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.lj.core.ecs.entity.PlayerEntity
import io.vertx.core.json.JsonObject
import kt.scaffold.tools.logger.Logger
import kotlin.reflect.KClass

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,property = "@class")
@JsonSubTypes(
    JsonSubTypes.Type(value = PlayerEntity::class, name = "player")
)

open abstract class Entity {
    companion object {

        inline fun <reified T> new():T where T:Entity{
            val clz = T::class.java
            var mCreate = clz.getDeclaredConstructor()
            mCreate.isAccessible = true

            val entity = mCreate.newInstance()
            entity.instanceId = IdFactory.newInstanceId()

            if (!MasterEntity.entities.containsKey(clz)){
                MasterEntity.entities[clz] = mutableListOf()
            }
            MasterEntity.entities[clz]!!.add(entity)

            return entity
        }

        inline fun <reified T> create():T where T:Entity{
            val entity = new<T>()
            entity._id = entity.instanceId
            MasterEntity.addChild(entity)
            entity.awake()
            Logger.debug("EntityFactory->Create, ${T::class.java} = ${entity.instanceId}")
            return entity
        }

        inline fun <reified T> create(initData: Any):T where T:Entity{
            val entity = new<T>()
            entity._id = entity.instanceId
            MasterEntity.addChild(entity)
            entity.awake(initData)
            Logger.debug("EntityFactory->Create, ${T::class.java} = ${entity.instanceId}, $initData")
            return entity
        }

        inline fun <reified T> createWithParent(parent: Entity):T where T:Entity{
            val entity = new<T>()
            entity._id = entity.instanceId
            parent.addChild(entity)
            entity.awake()
            Logger.debug("EntityFactory->createWithParent, ${T::class.java} = ${entity.instanceId}")
            return entity
        }

        inline fun <reified T> createWithParent(parent: Entity, initData:Any):T where T:Entity{
            val entity = new<T>()
            entity._id = entity.instanceId
            parent.addChild(entity)
            entity.awake(initData)
            Logger.debug("EntityFactory->createWithParent, ${T::class.java} = ${entity.instanceId}, $initData")
            return entity
        }

        fun destroy(entity: Entity){
            entity.onDestroy()
            entity.dispose()
        }
    }


    var _id:Long = 0
    var docName:String = "user"  //DB中的Document名称

    @JsonIgnore
    var instanceId:Long = 0

    @JsonIgnore
    var parent:Entity?=null
        @JsonIgnore
        get() {
            return field
        }
        @JsonIgnore
        set(value) {
            field = value

            value?.removeChild(this)
            value?.addChild(this)

            this.onSetParent(value)
        }

    @JsonIgnore
    var children = mutableListOf<Entity>()
    @JsonIgnore
    var type2Children = mutableMapOf<Any, MutableList<Entity>>()

    val components = mutableMapOf<Any, Component>()
    @JsonIgnore
    val updateComponentJson:JsonObject = JsonObject()
    @JsonIgnore
    val removeComponentJson:JsonObject = JsonObject()


    open fun awake(){}

    open fun awake(initData:Any){}

    open fun onSetParent(entity:Entity?){

    }

    open fun onDestroy(){
        this.dispose()
    }

    fun dispose(){
        this.children.forEach(){it->
            Entity.destroy(it)
        }
        children.clear()
        type2Children.clear()

        this.components.values.forEach(){it->
            it.onDestroy()
            it.dispose()
        }
        this.components.clear()
        this.parent?.removeChild(this)
        this.instanceId = 0

        MasterEntity.entities[this::class.java]?.remove(this)
    }

    fun <T> getParentT():T where T:Entity{
        return this.parent as T
    }

    fun addChild(child:Entity){
        this.children.add(child)

        if(!this.type2Children.containsKey(child::class.java)){
            this.type2Children[child::class.java] = mutableListOf()
        }
        this.type2Children[child::class.java]?.add(child)

        child.parent = this
    }

    fun removeChild(child: Entity){
        this.children.remove(child)
        this.type2Children[child::class.java]?.remove(child)

        child.parent = null
    }

    @JsonIgnore
    fun getChildren(): Array<Entity> {
        return this.children.toTypedArray()
    }

    @JsonIgnore
    inline fun <reified T:Entity> getTypeChildren(): Array<Entity>? {
        return this.type2Children[T::class.java]?.toTypedArray()
    }

    inline fun <reified T> addComponent(saveDb:Boolean = false):T where T:Component{
        val clz = T::class.java
        var mCreate = clz.getDeclaredConstructor()
        mCreate.isAccessible = true

        val c = mCreate.newInstance()
        c.entity = this
        c.isDisposed = false
        c.setup()

        this.components[clz.simpleName] = c;

        if (saveDb){
            updateComponentJson.put("components.${clz.simpleName}",JsonObject.mapFrom(c))
        }else{
            MasterEntity.allComponents.add(c)
        }

        return c
    }

    inline fun <reified T> addComponent(initData: Any):T where T:Component{
        val clz = T::class.java
        var mCreate = clz.getDeclaredConstructor()
        mCreate.isAccessible = true

        val c = mCreate.newInstance()
        c.entity = this
        c.isDisposed = false
        c.setup(initData)

        this.components[clz.simpleName] = c;

        MasterEntity.allComponents.add(c)

        return c
    }

    inline fun <reified T:Component> getComponent(): T? {

        return this.components[T::class.java.simpleName] as T
    }

    inline fun <reified T:Component> removeComponent(){

        val name = T::class.java.simpleName
        this.components.remove(name)

        removeComponentJson.put("components.${name}", 1)
    }



}

