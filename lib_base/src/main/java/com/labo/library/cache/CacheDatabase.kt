package com.labo.library.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.labo.lib.tool.utils.AppGlobals

/**
 * room数据库离线缓存框架
 */
@Database(entities = [Cache::class], version = 1)
abstract class CacheDatabase : RoomDatabase() {

    companion object {
        private const val DATA_BASE_NAME = "app_lib_cache"
        private var database: CacheDatabase
        fun get(): CacheDatabase {
            return database
        }

        init {
            val context = AppGlobals.getContext()!!.applicationContext
            database =
                Room.databaseBuilder(context, CacheDatabase::class.java, DATA_BASE_NAME).build()
        }
    }

    abstract val cacheDao: CacheDao
}