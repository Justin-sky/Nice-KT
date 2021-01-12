package com.lj.services

import com.lj.core.net.parser.NetMsgParser
import kt.scaffold.Application
import kt.scaffold.common.MicroServiceVerticle
import kt.scaffold.tools.logger.Logger

class LoginVerticle:MicroServiceVerticle(){

    override suspend fun start() {
        super.start()

        val tcpServerOptions = Application.tcpServerOptions()
        val tcpServer = this.vertx.createNetServer(tcpServerOptions)

        tcpServer.connectHandler(){ socket ->
            Logger.debug("Connect ... ${socket.remoteAddress()} ")

            socket.handler(NetMsgParser.decode())

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