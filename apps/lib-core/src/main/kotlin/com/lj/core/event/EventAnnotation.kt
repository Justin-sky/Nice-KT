package com.lj.core.event

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class EventAnnotation(val eventID:String)