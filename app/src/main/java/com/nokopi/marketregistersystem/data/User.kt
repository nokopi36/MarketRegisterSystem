package com.nokopi.marketregistersystem.data

import androidx.room.*

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val userId: String,
    @ColumnInfo val userName: String,
    @ColumnInfo val userBalance: Int
)

@Dao
interface UserDao {
    @Insert
    fun insertAll(vararg user: User)

}

@Database(entities = [User::class], version = 1)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}