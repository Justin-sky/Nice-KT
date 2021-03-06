package com.lj.core.ecs

import kotlin.reflect.KClass


class  EventSubscribeCollection<T>{
    val subscribes = mutableListOf<EventSubscribe<T>>()
    private val action2Subscribes = mutableMapOf<(t:T)->Unit, EventSubscribe<T>>()

    fun add(action: (t:T) -> Unit): EventSubscribe<T>{
        val eventSubscribe = EventSubscribe<T>()
        eventSubscribe.eventAction = action

        subscribes.add(eventSubscribe)
        action2Subscribes[action] = eventSubscribe

        return  eventSubscribe
    }

    fun remove(action: (t:T) -> Unit){
        subscribes.remove(action2Subscribes[action])
        action2Subscribes.remove(action)
    }
}

class EventSubscribe<T>{
    lateinit var eventAction:(t:T)->Unit
}

class EventComponent :Component() {
    override var enable: Boolean = true
    val eventSubscribeCollections = mutableMapOf<KClass<*>, Any>()

    inline fun <reified T> publish(tEvent:T):T{
        val collection = this.eventSubscribeCollections[T::class]
        val eventSubscribeCollection = collection as? EventSubscribeCollection<T>
        if (eventSubscribeCollection != null){
            if (eventSubscribeCollection.subscribes.size == 0) return tEvent

            eventSubscribeCollection.subscribes.forEach { item->
                item.eventAction.invoke(tEvent)
            }
        }
        return tEvent
    }

    inline fun <reified T> subscribe(noinline action:(t:T)->Unit): EventSubscribe<T> {
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