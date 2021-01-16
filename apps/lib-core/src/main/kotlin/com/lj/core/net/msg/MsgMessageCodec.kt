package com.lj.core.net.msg

import com.lj.core.service.Msg
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec
import java.io.*


class MsgMessageCodec : MessageCodec<Msg,Msg> {

    override fun decodeFromWire(pos: Int, buffer: Buffer): Msg? {
        val b = ByteArrayInputStream(buffer.bytes)
        var msg: Msg? = null
        try {
            ObjectInputStream(b).use { o -> msg = o.readObject() as Msg }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return msg
    }

    override fun encodeToWire(buffer: Buffer, s: Msg) {
        val b = ByteArrayOutputStream()
        try {
            ObjectOutputStream(b).use { o ->
                o.writeObject(b)
                o.close()
                buffer.appendBytes(b.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun transform(s: Msg): Msg {
       return s;
    }

    override fun name(): String {
        return "msgCodec"
    }

    override fun systemCodecID(): Byte {
        return -1
    }
}