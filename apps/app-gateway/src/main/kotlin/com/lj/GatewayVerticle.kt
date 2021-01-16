package com.lj

import com.lj.core.net.SocketManager
import kt.scaffold.Application
import kt.scaffold.common.MicroServiceVerticle
import kt.scaffold.tools.logger.Logger


class GatewayVerticle :MicroServiceVerticle(){

     override suspend fun start() {
        super.start()

        val tcpServerOptions = Application.tcpServerOptions()
        val tcpServer = this.vertx.createNetServer(tcpServerOptions)

        tcpServer.connectHandler(){ socket ->
            Logger.debug("Connect ... ${socket.remoteAddress()} ")

            val socketId = socket.writeHandlerID()
            Logger.debug("Socket ID: $socketId")

            //保存Socket
            SocketManager.activeSocket(socketId, socket)

            //网关消息处理
            socket.handler(SocketManager.socketHandler(socketId, true))

            socket.closeHandler(){
                Logger.debug("socket close: $socketId")

                SocketManager.inactiveSocket(socketId)
                SocketManager.unbindUserid2Socket(socketId)
            }
        }

        tcpServer.exceptionHandler(){
            Logger.debug("exception ${it.message}")
        }

        val host = Application.config.getString("app.tcpServer.host")
        val port = Application.config.getInt("app.tcpServer.port")
        tcpServer.listen(port,host)
        Logger.info("GateWay Server listen: $host: $port")

    }
}