package kt.scaffold.ext

import kt.scaffold.tools.json.toShortJson
import org.slf4j.Logger
import kt.scaffold.tools.logger.Logger as KtLogger

fun Logger.trace(item: Any) {
    this.trace(item.toShortJson())
}

fun Logger.debug(item: Any) {
    this.debug(item.toShortJson())
}

fun Logger.info(item: Any) {
    this.info(item.toShortJson())
}

fun Logger.warn(item: Any) {
    this.warn(item.toShortJson())
}

fun Logger.error(item: Any) {
    this.error(item.toShortJson())
}

fun KtLogger.trace(item: Any) {
    appLogger.trace(item)
}

fun KtLogger.debug(item: Any) {
    appLogger.debug(item)
}

fun KtLogger.info(item: Any) {
    appLogger.info(item)
}

fun KtLogger.warn(item: Any) {
    appLogger.warn(item)
}

fun KtLogger.error(item: Any) {
    appLogger.error(item)
}

