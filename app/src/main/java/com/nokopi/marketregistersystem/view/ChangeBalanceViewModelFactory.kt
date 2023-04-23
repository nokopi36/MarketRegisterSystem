package com.nokopi.marketregistersystem.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.admin.UserInfoViewModel
import com.nokopi.marketregistersystem.data.User
import com.nokopi.marketregistersystem.data.UserDatabaseDao

class ChangeBalanceViewModelFactory(private val dataSource: UserDatabaseDao, private val user: User) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangeBalanceViewModel::class.java)) {
            return ChangeBalanceViewModel(dataSource, user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}