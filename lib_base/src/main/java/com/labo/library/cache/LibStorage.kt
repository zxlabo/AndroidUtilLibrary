package com.labo.library.cache

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * 采用Room数据库缓存；
 * 采用二进制序列化
 * 我们先存，此时已经存到了数据库。
 * 然后修改代码，新增类，去取。是不是娶不到。
 * 注意：
 * 1、对这个类进行操作，首先要在子线程进行操作。
 * 2、如果是二进制序列化，对象要实现 Serializable
 * 3、如果采用二进制对数据进行序列化，
 *    如果我们的对象，新增了属性，之前数据库中有数据，反序列化会抛出异常。此时拿到的对象是空的。
 */
object LibStorage {
    fun <T> saveCache(key: String, body: T) {
        val cache = Cache()
        cache.key = key
        cache.data = toByteArray(body)
        CacheDatabase.get().cacheDao.saveCache(cache)
    }

    fun <T> getCache(key: String): T? {
        val cache = CacheDatabase.get().cacheDao.getCache(key)
        return (if (cache?.data != null) {
            toObject(cache.data)
        } else null) as? T
    }

    fun deleteCache(key: String) {
        val cache = Cache()
        cache.key = key
        CacheDatabase.get().cacheDao.deleteCache(cache)
    }


    private fun <T> toByteArray(body: T): ByteArray? {
        var baos: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        try {
            baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            oos.writeObject(body)
            oos.flush()
            return baos.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            baos?.close()
            oos?.close()
        }

        return ByteArray(0)
    }

    private fun toObject(data: ByteArray?): Any? {
        var bais: ByteArrayInputStream? = null
        var ois: ObjectInputStream? = null
        try {
            bais = ByteArrayInputStream(data)
            ois = ObjectInputStream(bais)
            return ois.readObject()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            bais?.close()
            ois?.close()
        }

        return null
    }
}