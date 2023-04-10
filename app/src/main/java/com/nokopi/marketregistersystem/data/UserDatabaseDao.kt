package com.nokopi.marketregistersystem.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDatabaseDao {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM user_info_table WHERE id = :key")
    fun get(key: Int): User?

    @Query("SELECT user_id FROM user_info_table WHERE user_id = :user_id")
    fun getUserId(user_id: String): String?



}