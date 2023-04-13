package com.nokopi.marketregistersystem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.data.UserDatabaseDao

class SignUpViewModelFactory(private val dataSource: UserDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}