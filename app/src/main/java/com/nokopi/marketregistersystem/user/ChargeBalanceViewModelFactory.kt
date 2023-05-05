package com.nokopi.marketregistersystem.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.data.UserDatabaseDao

class ChargeBalanceViewModelFactory(private val userDataSource: UserDatabaseDao, private val inputId: String) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChargeBalanceViewModel::class.java)) {
            return ChargeBalanceViewModel(userDataSource, inputId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}