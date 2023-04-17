package com.nokopi.marketregistersystem.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admin_info_table")
data class Admin(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "admin_name")
    var adminName: String,

    @ColumnInfo(name = "password")
    var password: String
)