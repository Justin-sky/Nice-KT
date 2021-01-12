package com.lj.core.msg

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

@DataObject
 data class Msg(var seq:Int, var msgId:Int, var serverId:Int, var protoStr:String){

   constructor(json: JsonObject):
           this(json.getInteger("seq"),
               json.getInteger("msgId"),
               json.getInteger("serverId"),
               json.getString("protoStr"))

    fun toJson():JsonObject = JsonObject.mapFrom(this)

}