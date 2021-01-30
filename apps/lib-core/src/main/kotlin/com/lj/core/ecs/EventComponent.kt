package com.lj.core.ecs

import kotlin.reflect.KClass


class  EventSubscribeCollection<T> where T:KClass<*>{
    val subscribes = mutableListOf<EventSubscribe<T>>()
    val action2Subscribes = mutableMapOf<(t:T)->Unit, EventSubscribe<T>>()

    fun add(action: (t:T) -> Unit): EventSubscribe<T>{
        val eventSubscribe = EventSubscribe<T>()
        eventSubscribe.eventAction = action
        subscribes.add(eventSubscribe)

        return  eventSubscribe
    }

    fun remove(action: (t:T) -> Unit){
        subscribes.remove(action2Subscribes[action])
        action2Subscribes.remove(action)
    }
}

class EventSubscribe<T>{
    lateinit var eventAction:(t:T)->Unit
    var coroutine:Boolean = false

    fun asCoroutine(){
        coroutine = true
    }
}

class EventComponent :Component() {
    override var enable: Boolean = true

    val eventSubscribeCollections = mutableMapOf<KClass<*>, Any>()
    val coroutineEventSubscribeQueue = mutableMapOf<Any, Any>()

    override fun update() {
        coroutineEventSubscribeQueue.forEach{ item->
            val event = item.key
            val eventSubscribe = item.value
            val field = eventSubscribe.javaClass.getField("EventAction")
            val value = field.get(eventSubscribe)
            value.javaClass.getMethod("Invoke").invoke(value, event)
        }
        coroutineEventSubscribeQueue.clear()
    }

    inline fun <reified T:KClass<*>> publish(tEvent:T):T{
        val collection = this.eventSubscribeCollections[T::class]
        val eventSubscribeCollection = collection as? EventSubscribeCollection<T>
        if (eventSubscribeCollection != null){
            if (eventSubscribeCollection?.subscribes.size == 0) return tEvent

            eventSubscribeCollection?.subscribes.forEach { item->
                if (!item.coroutine){
                    item.eventAction.invoke(tEvent)
                }else{
                    coroutineEventSubscribeQueue[tEvent] = item
                }
            }
        }
        return tEvent
    }

    inline fun <reified T:KClass<*>> subscribe(noinline action:(t:T)->Unit): EventSubscribe<T> {
        val collection = eventSubscribeCollections[T::class]
        var eventSubscribeCollection = collection as? EventSubscribeCollection<T>
        if (eventSubscribeCollection == null) {
            eventSubscribeCollection = EventSubscribeCollection<T>()
            eventSubscribeCollections[T::class] = eventSubscribeCollection
        }
        return eventSubscribeCollection.add(action)
    }

    inline fun <reified T : KClass<*>> unSubscribe(noinline action:(t:T)->Unit){
        val collection = eventSubscribeCollections[T::class]
        var eventSubscribeCollection = collection as? EventSubscribeCollection<T>
        eventSubscribeCollection?.remove(action)
    }

}