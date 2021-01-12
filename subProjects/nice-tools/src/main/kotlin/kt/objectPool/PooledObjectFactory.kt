package kt.objectPool

import kt.scaffold.tools.logger.Logger

abstract class PooledObjectFactory<T : Any> {

    abstract suspend fun createObjectAwait(): T

    open suspend fun wrapObject(pool: ObjectPool<T>): PooledObject<T>? {
        try {
            val obj = createObjectAwait()
            return PooledObject(target = obj, pool = pool)
        } catch (ex: Exception) {
            Logger.warn("Create ${this.javaClass.name} object failed.\n$ex")
            return null
        }

    }

    open fun destoryObject(target: T) {
//        Logger.debug("Destory pooled object: [${System.identityHashCode(obj)}]")
    }
}