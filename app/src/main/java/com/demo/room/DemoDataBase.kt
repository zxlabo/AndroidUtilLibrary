package com.demo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * 数据库：使用 @Database 注释的类，需要满足以下条件
 * 1）是扩展 RoomDatabase 的抽象类。
 * 2）在注解中添加与数据库关联的实体列表。
 * 3）包含具有 0 个参数且返回使用 @Dao 注释的类的抽象方法。
 * 4）在运行时，您可以通过调用 Room.databaseBuilder() 或 Room.inMemoryDatabaseBuilder() 获取 Database 的实例。
 */

/**
 * 1、在entities中添加与数据库关联的表；
 * 2、设置版本号 version。注意：只要新增表or表新增字段，都要增加版本号；
 * 3、exportSchema = true:Room 可以在编译时将数据库的架构信息导出为 JSON 文件。
 *   需要在app/build.gradle 文件中设置 room.schemaLocation 注释处理器属性。
 *
 */

@Database(entities = [DemoTable::class, User::class], version = 1, exportSchema = true)

abstract class DemoDataBase : RoomDatabase() {
    abstract fun cacheDao():DemoDao
    companion object {
        const val DATA_BASE_NAME = "CacheDataBase"
        private var dataBase: DemoDataBase? = null

        /**
         * 数据库升级，需要添加 addMigrations
         */
        val migration1_2=object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table table_cache add column cache_time")
            }
        }

        /**
         * 在运行时，您可以通过调用 Room.databaseBuilder() 或 Room.inMemoryDatabaseBuilder() 获取 Database 的实例。
         * inMemoryDatabaseBuilder：内存数据库
         * databaseBuilder：本地数据库
         */
        @Synchronized
        fun get(context: Context): DemoDataBase {
            if (dataBase == null) {
                dataBase = Room.databaseBuilder(context, DemoDataBase::class.java, DATA_BASE_NAME)
                    //允许在主线程操作数据库，默认是不允许。
                    //.allowMainThreadQueries()
                    //.addMigrations(migration1_2)
                    .build()
            }
            return dataBase!!
        }
    }

}
