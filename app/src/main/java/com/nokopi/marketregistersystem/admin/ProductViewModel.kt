package com.nokopi.marketregistersystem.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokopi.marketregistersystem.data.Product
import com.nokopi.marketregistersystem.data.ProductDatabaseDao
import kotlinx.coroutines.launch

class ProductViewModel(private val database: ProductDatabaseDao): ViewModel() {

    var productList: LiveData<List<Product>> = database.get()

    private var _isClickAddProduct: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isClickAddProduct: LiveData<Boolean>
        get() = _isClickAddProduct

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            database.delete(product)
        }
    }

    fun onClickAddProduct() {
        _isClickAddProduct.value = true
    }

}