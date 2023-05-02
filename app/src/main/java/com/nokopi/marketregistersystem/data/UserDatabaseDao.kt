package com.nokopi.marketregistersystem.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDatabaseDao {
    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user_info_table WHERE user_id = :user_id")
    suspend fun get(user_id: String): User

    @Query("SELECT user_id FROM user_info_table WHERE user_id = :user_id")
    suspend fun getUserId(user_id: String): String

    @Query("SELECT user_balance FROM user_info_table WHERE user_id = :user_id")
    suspend fun getUserBalance(user_id: String): Int

    @Query("SELECT * FROM user_info_table")
    fun getUserName(): LiveData<List<User>>

}