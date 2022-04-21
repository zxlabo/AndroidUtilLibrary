package com.apple.dance.room

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 *
 * Dao:data access object缩写，数据访问对象。
 * Dao：包含用于访问数据库的方法。比如：增删改查；
 */
@Dao
interface DemoDao {
    /**
     * 按照主键查询并且限制是1，所以可以直接返回对象。
     * 也可以返回集合：List<Cache>
     */
    @Query("select * from table_cache where cacheId =:kewWord limit 1")
    fun query(kewWord: String): DemoTable

    /**
     * 可以通过LiveData，以观察者的形式获取数据，可以避免不必要的npe；
     * 注意：它可以监听数据库表中数据的变化，一旦发生了insert、update、delete。
     *      Room会自动读取表中的最新数据。发送给观察者。
     */
    @Query("select * from table_cache ")
    fun query2(): LiveData<List<DemoTable>>

    /**
     * onConflict:冲突策略
     */
    @Insert(entity = DemoTable::class, onConflict = OnConflictStrategy.REPLACE)
    fun insert(cache: DemoTable)

    @Delete(entity = DemoTable::class)
    fun delete(cache: DemoTable)

    @Update(entity = DemoTable::class)
    fun update(cache: DemoTable)
}