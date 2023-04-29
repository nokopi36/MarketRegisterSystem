package com.nokopi.marketregistersystem.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.data.ProductDatabaseDao

class AddProductViewModelFactory(private val dataSource: ProductDatabaseDao): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddProductViewModel::class.java)) {
            return AddProductViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}