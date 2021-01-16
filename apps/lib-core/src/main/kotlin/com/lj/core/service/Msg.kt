package com.lj.core.service

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

/**
 * @DataObject 注解用于服务器生成Rpc用
 */
@DataObject
 data class Msg(
    var seq:Int,
    var msgId:Short,
    var serverType:Byte,
    var serverId:Short,
    var userId:Long,
    var base64Msg:String){

   constructor(json: JsonObject):
           this(json.getInteger("seq"),
               json.getInteger("msgId").toShort(),
               json.getInteger("serverType").toByte(),
               json.getInteger("serverId").toShort(),
               json.getLong("userId"),
               json.getString("base64Msg"))

    fun toJson():JsonObject = JsonObject.mapFrom(this)

    fun copy():Msg{
        return Msg(seq,msgId,serverType,serverId,userId,base64Msg)
    }
}