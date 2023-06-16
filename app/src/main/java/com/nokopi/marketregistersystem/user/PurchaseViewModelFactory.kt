package com.nokopi.marketregistersystem.user

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.data.ProductDatabaseDao
import com.nokopi.marketregistersystem.data.UserDatabaseDao

class PurchaseViewModelFactory(private val userDataSource: UserDatabaseDao, private val productDataSource: ProductDatabaseDao, private val inputId: String, private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PurchaseViewModel::class.java)) {
            return PurchaseViewModel(userDataSource, productDataSource, inputId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}