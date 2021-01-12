package com.lj.services

import com.lj.core.eventBus.EventBusAddress
import com.lj.core.net.msg.Msg
import com.lj.core.net.parser.NetMsgParser
import io.vertx.core.Handler
import io.vertx.core.buffer.Buffer
import io.vertx.core.parsetools.RecordParser
import io.vertx.kotlin.servicediscovery.getRecordAwait
import io.vertx.kotlin.servicediscovery.getRecordsAwait
import kt.scaffold.Application
import kt.scaffold.common.MicroServiceVerticle
import kt.scaffold.tools.logger.Logger
import io.vertx.servicediscovery.Record
import kotlinx.coroutines.launch


class GatewayVerticle :MicroServiceVerticle(){

     override suspend fun start() {
        super.start()

        val tcpServerOptions = Application.tcpServerOptions()
        val tcpServer = this.vertx.createNetServer(tcpServerOptions)

        tcpServer.connectHandler(){ socket ->
            Logger.debug("Connect ... ${socket.remoteAddress()} ")

            socket.handler(NetMsgParser.decode())

            //TODO test
            socket.handler { buffer ->
                Logger.debug("receive msg: ${buffer.toString()}")
//                vertx.eventBus().request<String>(EventBusAddress.PROTO_ADDRESS, buffer.toString()) { response ->
//                    if (response.succeeded()) {
//                        Logger.debug(response.result().body())
//
//                        socket.write(response.result().body())
//                    }
//                }

               // discovery.getRecordAwait({r:Record->1000 == r.metadata.getInteger("server_id")})
                launch{
                    val serverId = 1000

                    var record:Record? = null;
                    if(serverId>0){
                        record = discovery.getRecordAwait { r:Record ->
                            1000 == r.metadata.getInteger("server_id")
                        }
                    }
                    if (record == null){
                        val records = discovery.getRecordsAwait { r:Record->
                            r.name.equals(EventBusAddress.SERVICE_GAMESERVER_NAME)
                        }
                        if(records!= null && records.size>0){
                            record = records.shuffled().get(0)
                        }
                    }

                    if(record!= null){
                        val reference =  discovery.getReference(record)
                        val service = reference.getAs(GameServerService::class.java)

                        val msg = Msg(1, 1, 100, "test")
                        service.dispatchMsg(msg, Handler {rs->
                            Logger.debug(rs.result());
                        })

                        reference.release()
                    }
                }

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