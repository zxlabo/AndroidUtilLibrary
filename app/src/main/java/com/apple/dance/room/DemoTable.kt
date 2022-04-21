package com.apple.dance.room

import android.graphics.Bitmap
import androidx.room.*
import org.jetbrains.annotations.NotNull

/**
 * Entity：表示数据库中的表。
 */

@Entity(tableName = "table_cache")
class DemoTable {
    /**
     * PrimaryKey:表的主键，autoGenerate是否自动生成；
     * NotNull：主键不能为空；
     * ColumnInfo：设置字段的名称和默认值；
     */
    @PrimaryKey(autoGenerate = false)
    @NotNull
    @ColumnInfo(name = "cacheId", defaultValue = "")
    var cache_Key: String = ""

    /**
     * Ignore：表示该字段不入数据库；
     */
    @Ignore
    var bitmap: Bitmap? = null

    /**
     * Embedded:表示关联另一张表
     */
    @Embedded
    var user: User? = null

}

