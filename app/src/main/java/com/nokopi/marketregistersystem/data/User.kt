package com.nokopi.marketregistersystem.data

import androidx.room.*

@Entity(tableName = "user_info_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "user_id")
    var userId: String,

    @ColumnInfo(name = "user_name")
    var userName: String,

    @ColumnInfo(name = "user_balance")
    var userBalance: Int
)