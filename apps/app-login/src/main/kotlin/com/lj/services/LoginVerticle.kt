package com.lj.services

import com.lj.core.net.SocketManager
import kt.scaffold.Application
import kt.scaffold.common.MicroServiceVerticle
import kt.scaffold.tools.logger.Logger

class LoginVerticle:MicroServiceVerticle(){

    override suspend fun start() {
        super.start()

        val tcpServerOptions = Application.tcpServerOptions()
        val tcpServer = this.vertx.createNetServer(tcpServerOptions)


        tcpServer.connectHandler(){ socket ->
            Logger.debug("Connect ， ${socket.writeHandlerID()}... ${socket.remoteAddress()} ")
            val socketId = socket.writeHandlerID()
            Logger.debug("Socket ID: $socketId")
            SocketManager.socketMap.put(socketId,socket)
            SocketManager.activeSocketMap.put(socketId,System.currentTimeMillis())


            socket.handler(SocketManager.socketHandler(socketId))

        }

        tcpServer.exceptionHandler(){
            Logger.debug("exception ${it.message}")
        }

        val host = Application.config.getString("app.loginServer.host")
        val port = Application.config.getInt("app.loginServer.port")
        tcpServer.listen(port,host)
        Logger.info("Login Server listen: $host: $port")

        //心跳检查
        vertx.setPeriodic(1000*30){ t->
            var iterator = SocketManager.activeSocketMap.entries.iterator();
            while (iterator.hasNext()){
                val entry = iterator.next()
                val time = System.currentTimeMillis() - entry.value
                if(time > 1000*60){

                    Logger.debug("SocketId: ${entry.key} clearn")
                    //从SocketMap删除
                    SocketManager.socketMap.remove(entry.key)?.close()
                    //从activeSocketMap删除
                    iterator.remove()
                }
            }
        }
    }
}