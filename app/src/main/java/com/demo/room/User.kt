package com.demo.room

import androidx.room.*
import org.jetbrains.annotations.NotNull

@Entity(tableName = "table_user")
class User {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    var user_id: Long = 0

    var name: String = ""
}