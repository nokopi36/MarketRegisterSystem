package com.nokopi.marketregistersystem.data

import androidx.room.*

@Entity(tableName = "user_info_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "user_name")
    val userName: String,

    @ColumnInfo(name = "user_balance")
    val userBalance: Int
)