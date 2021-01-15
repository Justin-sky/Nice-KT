package com.lj.core.common
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class HandlerAnnotation(val opcode:Short)