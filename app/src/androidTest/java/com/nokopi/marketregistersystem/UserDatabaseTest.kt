package com.nokopi.marketregistersystem

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nokopi.marketregistersystem.data.User
import com.nokopi.marketregistersystem.data.UserDatabase
import com.nokopi.marketregistersystem.data.UserDatabaseDao
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UserDatabaseTest {

    private lateinit var userDao: UserDatabaseDao
    private lateinit var db: UserDatabase

    @Before
    fun createDB() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userDao = db.userDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }


    @Test
    fun insertAndGet() {
        val user = User(userId = "test", userName = "Hiyama", userBalance = 10000)
        userDao.insert(user)
        val userInfo = userDao.getUserId("test")
        assertEquals(userInfo, "test")
    }
}