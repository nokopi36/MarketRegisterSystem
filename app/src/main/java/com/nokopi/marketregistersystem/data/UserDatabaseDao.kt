package com.nokopi.marketregistersystem.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDatabaseDao {
    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM user_info_table WHERE user_id = :user_id")
    suspend fun get(user_id: String): User?

    @Query("SELECT user_id FROM user_info_table WHERE user_id = :user_id")
    suspend fun getUserId(user_id: String): String



}