package com.lj.core.net

import com.lj.core.net.msg.MessageDispatcher
import com.lj.core.net.msg.Msg
import io.vertx.core.buffer.Buffer
import io.vertx.core.net.NetSocket
import io.vertx.core.parsetools.RecordParser
import kotlinx.coroutines.coroutineScope
import kt.scaffold.tools.logger.Logger

object SocketManager {
    val socketMap = mutableMapOf<String, NetSocket>()
    val activeSocketMap = mutableMapOf<String,Long>()


     fun socketHandler(socketId:String):RecordParser{
        val head_len = 4+4+4+4
        val parser = RecordParser.newFixed(head_len)

        var size = -1
        var seq:Int = -1
        var msgId:Int = -1
        var serverId:Int = -1

        parser.setOutput(){ buffer->
            if(-1 == size){
                seq = buffer.getInt(0)
                msgId = buffer.getInt(0+4)
                serverId = buffer.getInt(0+4+4)

                val protoSize = buffer.getInt(0+4+4+4)
                parser.fixedSizeMode(protoSize)
            }else{
                val buff = buffer.getBytes()
                try {
                    val msg = Msg(seq,msgId,serverId, String(buff))

                    MessageDispatcher.dispatch(socketId,msg)

                }catch (e:Exception){
                    Logger.error("message eror: $seq,$msgId,$serverId,${e.cause}")
                }
            }
        }
        return parser
    }

    /**
     * 发送消息到客户端
     * 消息格式：
     *  seq【4】 + msgID【4】 + serverId【4】 + size【4】 + ...bytes
     */
    fun sendMsg(socketId:String,seq:Int, msgId:Int,serverId:Int, protoBytes:ByteArray){

        val socket:NetSocket? = socketMap.get(socketId)
        try {
            val buffer = Buffer.buffer()
            buffer.appendInt(seq)
            buffer.appendInt(msgId)
            buffer.appendInt(serverId)
            buffer.appendInt(protoBytes.size)
            buffer.appendBytes(protoBytes)
            if (socket != null) {
                socket.write(buffer)
            }
        }catch (e:Exception){
            Logger.error("send msg error，msgID:$msgId, ${e.cause}")
        }
    }
}