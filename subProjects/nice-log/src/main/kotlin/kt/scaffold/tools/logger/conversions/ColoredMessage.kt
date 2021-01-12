package kt.scaffold.tools.logger.conversions

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.pattern.ClassicConverter
import ch.qos.logback.classic.spi.ILoggingEvent
import kt.scaffold.tools.logger.AnsiColor

class ColoredMessage :ClassicConverter(){
    override fun convert(event: ILoggingEvent): String {
        return if (useColor(event.loggerName)) {
            when (event.level) {
                Level.DEBUG -> AnsiColor.blue(event.message)
                Level.INFO -> AnsiColor.green(event.message)
                Level.WARN -> AnsiColor.yellow(event.message)
                Level.ERROR -> AnsiColor.red(event.message)
                else -> event.message
            }
        } else {
            event.message
        }
    }

    private fun useColor(loggerName:String):Boolean{
        if (loggerName == "App") return true

        return if (this.optionList.isNullOrEmpty()){
            false
        }else{
            this.optionList.find {
                loggerName.startsWith("${it}.")
            }!=null
        }
    }
}