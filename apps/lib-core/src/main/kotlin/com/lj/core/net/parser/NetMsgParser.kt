package com.lj.core.net.parser

import io.vertx.core.parsetools.RecordParser

object NetMsgParser {

    fun encode(){

    }

    fun decode():RecordParser{
        val parser  = RecordParser.newFixed(4)
        var size = -1
        parser.setOutput{ buffer ->
            if(-1 == size){
                size = buffer.getInt(0)
                parser.fixedSizeMode(size)
            }else{
                val buf = buffer.getBytes()


                parser.fixedSizeMode(4)
                size = -1
            }

        }
        return parser
    }

}