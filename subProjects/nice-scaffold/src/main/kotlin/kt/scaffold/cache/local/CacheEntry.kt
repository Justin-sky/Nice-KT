package kt.scaffold.cache.local

class CacheEntry(val content: ByteArray, val expireTimeInMs: Long = 0) {

    private val createTime = System.currentTimeMillis()

    fun isExpired(): Boolean {
        return if (expireTimeInMs > 0) {
            val now = System.currentTimeMillis()
            createTime + expireTimeInMs < now
        } else {
            false
        }
    }
}