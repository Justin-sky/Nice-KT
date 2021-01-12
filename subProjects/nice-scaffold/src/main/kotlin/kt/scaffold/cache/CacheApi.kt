package kt.scaffold.cache

import java.time.Instant
import java.time.LocalDateTime

interface CacheApi {

    fun exists(key: String): Boolean

    fun del(key: String)

    //<editor-fold desc="For String Value">
    fun get(key: String): String {
        return getBytes(key).toString(Charsets.UTF_8)
    }

    fun getOrElse(key: String, default: () -> String): String {
        val value = getBytesOrNull(key)
        return value?.toString(Charsets.UTF_8) ?: default()
    }

    fun getOrNull(key: String): String? {
        val value = getBytesOrNull(key)
        return value?.toString(Charsets.UTF_8)
    }

    fun getOrPut(key: String, expirationInMs: Long = 0, supplier: () -> String): String {
        val value = getOrNull(key)
        return if (value == null) {
            val newValue = supplier()
            set(key, newValue, expirationInMs)
            newValue
        } else {
            value
        }
    }

    fun set(key: String, value: String) {
        setBytes(key, value.toByteArray(Charsets.UTF_8))
    }

    fun set(key: String, value: String, expirationInMs: Long) {
        setBytes(key, value.toByteArray(Charsets.UTF_8), expirationInMs)
    }

    fun set(key: String, value: String, cleaningTime: Instant) {
        setBytes(key, value.toByteArray(Charsets.UTF_8), cleaningTime)
    }
    //</editor-fold>

    //<editor-fold desc="For Bytes Value">
    fun getBytes(key: String): ByteArray

    fun getBytesOrElse(key: String, default: () -> ByteArray): ByteArray

    fun getBytesOrNull(key: String): ByteArray?

    fun getBytesOrPut(key: String, expirationInMs: Long = 0, supplier: () -> ByteArray): ByteArray {
        val value = getBytesOrNull(key)
        return if (value == null) {
            val newValue = supplier()
            setBytes(key, newValue, expirationInMs)
            newValue
        } else {
            value
        }
    }

    fun setBytes(key: String, value: ByteArray)

    fun setBytes(key: String, value: ByteArray, expirationInMs: Long)

    fun setBytes(key: String, value: ByteArray, cleaningTime: Instant) {
        val now = Instant.now().toEpochMilli()
        val diffTime = cleaningTime.toEpochMilli() - now
        if (diffTime <= 0) {
            setBytes(key, value)
        } else {
            setBytes(key, value, diffTime)
        }
    }
    //</editor-fold>
}