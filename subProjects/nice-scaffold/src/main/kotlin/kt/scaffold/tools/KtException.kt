package kt.scaffold.tools

class KtException(msg: String, cause: Throwable?) : RuntimeException(msg, cause) {

    constructor(ex: Exception) : this(ex.message ?: "", ex)

    constructor(msg: String) : this(msg, null)
}