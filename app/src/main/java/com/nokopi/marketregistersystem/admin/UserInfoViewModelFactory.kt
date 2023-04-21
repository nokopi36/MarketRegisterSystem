package com.nokopi.marketregistersystem.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.data.User
import com.nokopi.marketregistersystem.data.UserDatabaseDao

class UserInfoViewModelFactory(private val dataSource: UserDatabaseDao, private val user: User) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserInfoViewModel::class.java)) {
            return UserInfoViewModel(dataSource, user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}