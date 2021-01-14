package com.lj.core.net.msg

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

/**
 * @DataObject 注解用于服务器生成Rpc用
 */
@DataObject
 data class Msg(var seq:Int, var msgId:Short,var serverType:Byte, var serverId:Short, var base64Msg:String){

   constructor(json: JsonObject):
           this(json.getInteger("seq"),
               json.getInteger("msgId").toShort(),
               json.getInteger("serverType").toByte(),
               json.getInteger("serverId").toShort(),
               json.getString("base64Msg"))

    fun toJson():JsonObject = JsonObject.mapFrom(this)

}