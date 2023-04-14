package com.nokopi.marketregistersystem.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Admin::class], version = 1, exportSchema = false)
abstract class AdminDatabase : RoomDatabase() {
    abstract val adminDatabaseDao: AdminDatabaseDao
    companion object {
        @Volatile
        private var INSTANCE: AdminDatabase? = null
        fun getInstance(context: Context): AdminDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AdminDatabase::class.java,
                        "admin_info_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}