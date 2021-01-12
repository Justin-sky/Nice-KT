package kt.scaffold.tools.logger.conversions

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.pattern.ClassicConverter
import ch.qos.logback.classic.spi.ILoggingEvent
import kt.scaffold.tools.logger.AnsiColor

class ColoredLevel: ClassicConverter() {
    override fun convert(event: ILoggingEvent): String {
        return when(event.level){
            Level.DEBUG -> AnsiColor.blue("debug")
            Level.INFO -> AnsiColor.green("info")
            Level.WARN -> AnsiColor.yellow("warn")
            Level.ERROR -> AnsiColor.red("error")
            else -> ""
        }
    }
}