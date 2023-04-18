package com.nokopi.marketregistersystem.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.data.AdminDatabaseDao

class AdminSignInViewModelFactory(private val dataSource: AdminDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminSignInViewModel::class.java)) {
            return AdminSignInViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}