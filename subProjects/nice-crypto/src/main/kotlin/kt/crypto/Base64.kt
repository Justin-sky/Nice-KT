package kt.crypto

import java.util.*

fun ByteArray.encodeBase64():String{
    return Base64.getEncoder().encodeToString(this)
}

fun String.decodeBase64():ByteArray{
    return Base64.getDecoder().decode(this)
}
