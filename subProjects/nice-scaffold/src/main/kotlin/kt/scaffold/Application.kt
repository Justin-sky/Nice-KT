package kt.scaffold

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import io.vertx.core.AsyncResult
import io.vertx.core.DeploymentOptions
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.core.json.JsonObject
import io.vertx.core.net.NetServerOptions
import jodd.exception.ExceptionUtil
import jodd.io.FileNameUtil
import jodd.io.FileUtil
import jodd.util.ClassLoaderUtil
import kt.scaffold.ext.changeWorkingDir
import kt.scaffold.ext.filePathJoin
import kt.scaffold.tools.KtException
import kt.scaffold.tools.json.toShortJson
import kt.scaffold.tools.logger.Logger
import org.apache.commons.lang3.SystemUtils
import java.io.File
import java.lang.RuntimeException
import java.lang.management.ManagementFactory
import java.net.InetAddress
import java.net.URL
import java.util.*
import java.util.concurrent.CompletableFuture
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager as SpiClusterZookeeperZookeeperClusterManager
import io.vertx.kotlin.core.deployVerticleAwait
import kotlin.Exception

@Suppress("MemberVisibilityCanBePrivate", "HasPlatformType", "ObjectPropertyName")
object Application {

    private val startHandlers = mutableMapOf<Int, MutableList<() -> Unit>>()
    private val stopHandlers = mutableMapOf<Int, MutableList<() -> Unit>>()

    val config: Config
    var appHome: String
    val classLoader = Application::class.java.classLoader
    val inProductionMode: Boolean

    private var _vertx: Vertx? = null
    private const val ktPropertiesUrlKey = "kt.properties.url"


    val vertx:Vertx
        get(){
            if (_vertx == null){
                throw KtException("Application has not initialized vertx.")
            }
            return _vertx!!
        }

    private var _vertoptions: VertxOptions? = null
    val vertxOptions: VertxOptions
        get() {
            if (_vertoptions == null) {
                throw KtException("Application has not initialized vertx.")
            }
            return _vertoptions!!
        }


    init {
        writePidFile()

        // setup use SLF4JLogDelegateFactory
        // ref: https://vertx.io/docs/vertx-core/kotlin/#_using_another_logging_framework
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory")

        loadProperties()

        appHome = SystemUtils.getUserDir().absolutePath
        val confFolder = File(filePathJoin(SystemUtils.getUserDir().absolutePath, "conf"))
        if (confFolder.exists().not()) {
            if (SystemUtils.getUserDir().name == "bin" &&
                SystemUtils.getUserDir().parentFile.hasFile("conf${File.separator}application.conf")) {
                appHome = SystemUtils.getUserDir().parent
            }
        }

        var logbackXmlPath = filePathJoin(appHome, "conf", "logback.xml")
        if(File(logbackXmlPath).exists().not()){
            logbackXmlPath = ClassLoaderUtil.getDefaultClassLoader().getResource("conf/logback.xml")!!.path
            setupConfPathProperty("logback.configurationFile", logbackXmlPath)
        }else{
            setupConfPathProperty("logback.configurationFile", logbackXmlPath)
        }

        val currentDir = File("").absolutePath
        Logger.debug("current dir: $currentDir")
        Logger.debug("appHome : $appHome")
        Logger.debug("""-Dlogback.configurationFile : ${System.getProperty("logback.configurationFile")}""")

        Logger.debug("Change working dir to appHome")
        changeWorkingDir(appHome)

        var confPath = filePathJoin(appHome, "conf", "application.conf")
        if (File(confPath).exists().not()){
            confPath = ClassLoaderUtil.getDefaultClassLoader().getResource("conf/application.conf")!!.path
        }

        if (System.getProperties().containsKey("config.url")) {
            Logger.debug("""-Dconfig.url : ${System.getProperty("config.url")}""")
        } else {
            setupConfPathProperty("config.file", confPath)
            Logger.debug("""-Dconfig.file : ${System.getProperty("config.file")}""")
        }

        config = ConfigFactory.load()

        inProductionMode = config.getBoolean("app.tcpServer.productionMode")

        this.regOnStartHandler(Int.MIN_VALUE) {
            Logger.info("Application start...")
        }

        this.regOnStopHanlder(Int.MAX_VALUE){
            Logger.info("Application stop...")
            val stopVertxFuture = CompletableFuture<Boolean>()
            vertx.close { res ->
                if (res.failed()) {
                    Logger.error(ExceptionUtil.exceptionChainToString(res.cause()))
                    stopVertxFuture.complete(false)
                } else {
                    stopVertxFuture.complete(true)
                }
            }
            if (stopVertxFuture.get()) {
                Logger.info("Stop vertx successed.")
            } else {
                Logger.error("Stop vertx failed.")
            }
        }
    }

    private fun queryPid(): Long {
        return ManagementFactory.getRuntimeMXBean().name.split("@")[0].toLong()
    }

    private fun writePidFile() {
        val pidFilePath = System.getProperty("pidfile.path")
        if (!pidFilePath.isNullOrBlank()) {
            FileUtil.writeString(pidFilePath, queryPid().toString())
        }
    }

    private fun loadProperties(){
        try{
            val propertiesUr = System.getProperty(ktPropertiesUrlKey,"")

            if (propertiesUr.isNotBlank()){
                val url = URL(propertiesUr)
                url.openStream().use {
                    val properties = Properties()
                    properties.load(it)
                    properties.forEach { prop ->
                        System.setProperty(prop.key.toString(), prop.value.toString())
                    }
                }
            }
        }catch (ex:Exception){
            throw RuntimeException("Faild to load gloabl properties. Please check whether the config -D${ktPropertiesUrlKey} is valid.")
        }
    }

    private fun File.hasFile(path: String): Boolean {
        val fullPath = FileNameUtil.concat(this.path, path)
        return File(fullPath).exists()
    }

    private fun setupConfPathProperty(propName: String, default: String) {
        if (System.getProperties().containsKey(propName).not()) {
            // If it is not specified, it will be specified as our default value
            System.setProperty(propName, default)
        }
    }

    private fun logClusterNodeId(){
        if (vertxOptions.eventBusOptions.isClustered) {
            Logger.info("NodeId: ${vertxOptions.clusterManager.nodeID}")
            Logger.info("Cluster Nodes: ${vertxOptions.clusterManager.nodes.joinToString(", ")}")
        }
    }

    private fun buildVertxOptions():VertxOptions{
        try {
            val configContent = config.getConfig("app.vertx.options").root().unwrapped().toShortJson()

            val jsonOpts = JsonObject(configContent)
            val opts = VertxOptions(jsonOpts)

            // 需要修正 clusterHost, 不然不同主机节点之间的 EventBus 不会互通
            val hostIp = InetAddress.getLocalHost().hostAddress
            // conf/vertxOptions.json 文件里 如果有配置 clusterHost, 则以配置文件的为有效
            // 否则, 以获取到的主机的IP地址为 clusterHost
            // 如果获取不到主机的IP地址, 则继续使用默认的 localhost
            if (jsonOpts.containsKey("clusterHost").not() && hostIp.isNullOrBlank().not()) {
                // 配置文件中不包含 clusterHost 配置项, 并且获取到的主机IP不为空
                opts.eventBusOptions.host = hostIp
            }
            return opts
        } catch (ex: Exception) {
            throw RuntimeException("Failed to create vertx options: ${ex.message}")
        }
    }

    fun tcpServerOptions():NetServerOptions{
        val tcpCfg = config.getConfig("app.tcpServer.tcpOptions")

        val cfgMap = tcpCfg.root().map { Pair<String, Any>(it.key, it.value.unwrapped()) }.toMap().toMutableMap()
        val tcpOptionJson = JsonObject(cfgMap)

        val tcpOptions = NetServerOptions(tcpOptionJson)

        return tcpOptions
    }

    fun circuitBreakerOptions():JsonObject{
        val cbCfg = config.getConfig("app.vertx.circuitBreaker")
        val cfgMap = cbCfg.root().map { Pair<String, Any>(it.key, it.value.unwrapped()) }.toMap().toMutableMap()

        return JsonObject(cfgMap);
    }

    fun setupOnStartAndOnStop(){
        // 执行 OnStart 方法
        startHandlers.toSortedMap().values.forEach(){
            it.forEach(){
                it()
            }
        }
        // 注册 OnStop 方法
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                stopHandlers.toSortedMap().values.forEach(){
                    it.forEach(){
                        it()
                    }
                }
            }
        })
    }

    fun setupVertx(appVertx:Vertx?=null){
        if (_vertx != null){
            throw KtException("The vertx of Application has been initialized. Do not initialize it again.")
        }
        _vertx = appVertx?:createVertx()

        logClusterNodeId()
    }

    fun createVertx():Vertx{
        this._vertoptions = buildVertxOptions()

        if (this._vertoptions!!.eventBusOptions.isClustered) {
            Logger.info("Vertx: cluster mode")
            val future = CompletableFuture<Vertx>()

            val zookeeperConfig = JsonObject(config.getConfig("app.vertx.zookeeper").root().unwrapped().toShortJson())
            this._vertoptions!!.clusterManager = SpiClusterZookeeperZookeeperClusterManager(zookeeperConfig)

            Vertx.clusteredVertx(this._vertoptions) { event: AsyncResult<Vertx> ->
                if (event.failed()) {
                    throw KtException("Failed to create cluster mode Vertx: ${event.cause().message}")
                } else {
                    future.complete(event.result())
                }
            }
            return future.get()
        } else {
            Logger.info("Vertx: standalone mode")
            return Vertx.vertx(this._vertoptions)
        }
    }

    fun regOnStartHandler(priority: Int = 100, block: () -> Unit): Application {
        val handlerList = if (startHandlers.containsKey(priority)) {
            startHandlers[priority]!!
        } else {
            startHandlers.put(priority, mutableListOf())
            startHandlers[priority]!!
        }

        handlerList.add(block)
        return this
    }

    fun regOnStopHanlder(priority: Int = 100, block: () -> Unit): Application {
        val handlerList = if (stopHandlers.containsKey(priority)) {
            stopHandlers[priority]!!
        } else {
            stopHandlers.put(priority, mutableListOf())
            stopHandlers[priority]!!
        }

        handlerList.add(block)
        return this
    }

    suspend fun deployVerticle(className:String, verticleName:String){
        try{
            val workerMode = config.getBoolean("app.Verticles.$verticleName.workerMode")
            val instNum = config.getInt("app.Verticles.$verticleName.instance")
            vertx.deployVerticleAwait(
                className,
                DeploymentOptions().setWorker(workerMode).setInstances(instNum)
            )
        }catch (e:Exception){
            Logger.error("Deply verticle $className : $verticleName Exception, ${e.cause}")
        }

    }


}