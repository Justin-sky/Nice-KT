package kt.scaffold.net

import io.vertx.core.impl.ConcurrentHashSet
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.servicediscovery.getRecordAwait
import io.vertx.kotlin.servicediscovery.getRecordsAwait
import io.vertx.kotlin.servicediscovery.publishAwait
import io.vertx.kotlin.servicediscovery.unpublishAwait
import io.vertx.servicediscovery.Record
import io.vertx.servicediscovery.ServiceDiscovery
import io.vertx.servicediscovery.ServiceDiscoveryOptions
import io.vertx.servicediscovery.ServiceReference
import io.vertx.servicediscovery.types.EventBusService
import io.vertx.servicediscovery.types.HttpEndpoint
import io.vertx.servicediscovery.types.MessageSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kt.scaffold.Application
import kt.scaffold.tools.logger.Logger
import java.lang.Exception
import java.util.function.Consumer

object DiscoveryManager {

    private var discovery: ServiceDiscovery = ServiceDiscovery.create(
        Application.vertx,
        ServiceDiscoveryOptions().setBackendConfiguration(JsonObject())
    );
    private val registeredRecords = ConcurrentHashSet<Record>()

    suspend fun publishHttpEndpoint(name: String, host: String, port: Int) {
        val record: Record = HttpEndpoint.createRecord(name, host, port, "/")
        publishAwait(record)
    }

    suspend fun publishMessageSourceAwait(
        name: String,
        address: String,
        contentClass: Class<*>,
    ) {
        val record = MessageSource.createRecord(name, address, contentClass)
        publishAwait(record)
    }

    suspend fun publishMessageSourceAwait(name: String, address: String) {
        val record: Record = MessageSource.createRecord(name, address)
        publishAwait(record)
    }

    suspend fun publishEventBusServiceAwait(
        name: String,
        address: String,
        serviceClass: Class<*>,
        serverID: Int,
        serverType: Int
    )
    {
        val meta = JsonObject()
        meta.put("server_id",serverID)
        meta.put("server_type",serverType) //1 Game Server

        val record = EventBusService.createRecord(name, address, serviceClass, meta)
        publishAwait(record)
    }

    private suspend fun publishAwait(record: Record) {
        try {
            val re = discovery.publishAwait(record)
            registeredRecords.add(re)
        }catch (e: Exception){
            Logger.error("publish error: ${record.name},e:${e.cause}")
        }

    }

    fun unPublishAll() = runBlocking{
        registeredRecords.forEach(Consumer { record: Record ->
            GlobalScope.launch {
                discovery.unpublishAwait(record.registration)
            }
        })
    }

    suspend fun getServerRecordAwait(serverID:Int, serverType:Int): Record?{
        return discovery.getRecordAwait { r:Record ->
            r.metadata.getInteger("server_id") == serverID &&
            r.metadata.getInteger(("server_type")) == serverType
        };
    }

    suspend fun chooseServerRecordAwait(serverType: Int):Record?{
        val records = discovery.getRecordsAwait { r:Record ->
            r.metadata.getInteger("server_type") == serverType
        }
        if(records.isNotEmpty()){
            return records.shuffled()[0]
        }
       return null;
    }

    suspend fun getReferenceAwait(serverID:Int, serverType:Int): ServiceReference?{
        val record = getServerRecordAwait(serverID, serverType)
        val reference = discovery.getReference(record)
        return  reference
    }

    fun getReferenceAwait(record:Record):ServiceReference{
        return discovery.getReference(record)
    }

    fun close(){
        discovery.close()
    }

}