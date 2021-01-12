package com.lj.core.net.msg

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

/**
 * @DataObject 注解用于服务器生成Rpc用
 */
@DataObject
 data class Msg(var seq:Int, var msgId:Int, var serverId:Int, var protoStr:String){

   constructor(json: JsonObject):
           this(json.getInteger("seq"),
               json.getInteger("msgId"),
               json.getInteger("serverId"),
               json.getString("protoStr"))

    fun toJson():JsonObject = JsonObject.mapFrom(this)

}