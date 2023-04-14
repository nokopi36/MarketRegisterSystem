package com.nokopi.marketregistersystem.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AdminDatabaseDao {
    @Insert
    suspend fun insert(admin: Admin)

    @Update
    suspend fun update(admin: Admin)

    @Query("SELECT password FROM admin_info_table WHERE password = :password")
    suspend fun getPassword(password: String): String
}