package com.lj.core.net.msg

abstract class BaseHandler{
    abstract suspend fun process(socketId:String, msg:Msg);
}