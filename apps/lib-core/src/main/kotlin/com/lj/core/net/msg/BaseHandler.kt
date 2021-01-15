package com.lj.core.net.msg

import com.lj.core.service.Msg

abstract class BaseHandler{
    abstract  fun process(socketId:String, msg: Msg);
}