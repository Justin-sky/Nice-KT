package kt.objectPool

import java.io.Closeable
import java.time.Instant

@Suppress("MemberVisibilityCanBePrivate")
class PooledObject<T : Any>(val target: T, private val pool: ObjectPool<T>) : Closeable {

    internal var status = PooledObjectStatus.Idle
    internal val createTime = Instant.now()
    internal var lastBorrowTime = createTime
    internal var lastReturnTime = createTime

    private var _broken = false

    val broken: Boolean
        get() = _broken

    fun markBroken() {
        _broken = true
    }

    val identityHashCode: Int by lazy {
        System.identityHashCode(target)
    }

    override fun close() {
        if (status == PooledObjectStatus.Using) {
            status = PooledObjectStatus.Returning
            pool.returnObject(this)
        }
    }

    override fun toString(): String {
        val buf = StringBuilder()
        buf.appendLine("PooledObject:")
        buf.appendLine("  identityHashCode: $identityHashCode")
        buf.appendLine("  target type: ${target.javaClass.name}")
        buf.appendLine("  status: ${status.name}")
        buf.appendLine("  createTime: ${createTime}")
        buf.appendLine("  lastBorrowTime: ${lastBorrowTime}")
        buf.appendLine("  lastReturnTime: ${lastReturnTime}")
        buf.appendLine("  isBroken: $broken")

        return buf.toString()
    }
}

enum class PooledObjectStatus {
    Idle,
    Using,
    Returning,
    Broken,
}