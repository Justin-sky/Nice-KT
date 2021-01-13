package com.lj.core.net

import com.lj.core.net.msg.MessageDispatcher
import com.lj.core.net.msg.Msg
import com.lj.proto.Login
import io.vertx.core.buffer.Buffer
import io.vertx.core.net.NetSocket
import io.vertx.core.parsetools.RecordParser
import kt.scaffold.tools.logger.Logger

object SocketManager {
    val socketMap = mutableMapOf<String, NetSocket>()
    val activeSocketMap = mutableMapOf<String,Long>()


     fun socketHandler(socketId:String,isGate:Boolean = false):RecordParser{
        val headLen = 4 + 4 + 2 + 2 + 1
        val parser = RecordParser.newFixed(headLen)

        var size = -1
         //解析消息
         var seq:Int = -1
         var msgId:Short = -1
         var serverId:Short = -1
         var serverType:Byte = 0

        parser.setOutput(){ buffer->

            if(-1 == size){
                //首先，读取消息长度
                size = buffer.getInt(0)

                seq = buffer.getInt(0 + 4)
                msgId = buffer.getShort(0 + 4 + 4)
                serverId = buffer.getShort(0 + 4 + 4 + 2)
                serverType = buffer.getByte(0 + 4 + 4 +2 + 2)

                parser.fixedSizeMode(size - headLen + 4)
            }else{

                val buff = buffer.bytes
                try {
                    val msg = Msg(seq, msgId, serverType, serverId, String(buff))

                    if(isGate){
                        //分发到游戏服


                    }else{
                        //Login 服，直接处理
                        MessageDispatcher.dispatch(socketId,msg)
                    }

                }catch (e:Exception){
                    Logger.error("message eror: $seq,$msgId,$serverType,$serverId,${e.cause}")
                }

                //恢复读取长度
                parser.fixedSizeMode(headLen)
                size = -1
            }
        }
        return parser
    }

    /**
     * 发送消息到客户端
     * 消息格式：
     *  len[4] + seq【4】 + msgID【2】 + serverId【2】 + serverType【1】 + ...bytes
     */
    fun sendMsg(socketId:String,seq:Int,msgId:Short, serverId:Short,serverType:Byte, protoBytes:ByteArray){

        val socket:NetSocket? = socketMap.get(socketId)
        try {
            val buffer = Buffer.buffer()
            buffer.appendInt(protoBytes.size + 4 + 2 + 2 + 1)
            buffer.appendInt(seq)
            buffer.appendShort(msgId)
            buffer.appendShort(serverId)
            buffer.appendByte(serverType)
            buffer.appendBytes(protoBytes)
            if (socket != null) {
                socket.write(buffer)
            }
        }catch (e:Exception){
            Logger.error("send msg error，msgID:$msgId, ${e.cause}")
        }
    }
}