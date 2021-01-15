package com.lj.core.common

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ServiceHandlerAnnotation(val opcode:Short)