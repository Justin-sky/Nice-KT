package com.lj.core.msg

abstract class BaseHandler{
    abstract fun process(msg:Msg);
}