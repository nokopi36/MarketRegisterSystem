package com.nokopi.marketregistersystem.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokopi.marketregistersystem.data.Product
import com.nokopi.marketregistersystem.data.ProductDatabaseDao
import kotlinx.coroutines.launch

class AddProductViewModel(private val database: ProductDatabaseDao): ViewModel() {

    val productName: MutableLiveData<String> = MutableLiveData<String>()
    private val _productName: LiveData<String>
        get() = productName

    val productPrice: MutableLiveData<String> = MutableLiveData<String>()
    private val _productPrice: LiveData<String>
        get() = productPrice

    fun isProductNameNull(): Boolean {
        return !_productName.value.isNullOrBlank()
    }

    fun isProductPriceNull(): Boolean {
        return !_productPrice.value.isNullOrBlank()
    }

    fun addProduct() {
        viewModelScope.launch {
            val product = Product(0, _productName.value.toString(), _productPrice.value?.toInt() ?: -1)
            database.insert(product)
        }
    }

}