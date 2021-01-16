package com.lj

import com.lj.core.net.SocketManager
import com.lj.core.net.msg.MessageDispatcher
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

            //保存Socket
            SocketManager.activeSocket(socketId, socket)

            socket.handler(SocketManager.socketHandler(socketId))

            socket.closeHandler(){
                Logger.debug("socket close: $socketId")
                SocketManager.inactiveSocket(socketId)
                SocketManager.unbindUserid2Socket(socketId)
            }
        }

        tcpServer.exceptionHandler(){
            Logger.debug("exception ${it.message}")
        }

        val host = Application.config.getString("app.loginServer.host")
        val port = Application.config.getInt("app.loginServer.port")
        tcpServer.listen(port,host)
        Logger.info("Login Server listen: $host: $port")

    }
}