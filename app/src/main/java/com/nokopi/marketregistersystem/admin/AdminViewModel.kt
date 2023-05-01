package com.nokopi.marketregistersystem.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdminViewModel : ViewModel() {

    private val _goAllUser: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val goAllUser: LiveData<Boolean>
        get() = _goAllUser

    private val _goAddAdmin: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val goAddAdmin: LiveData<Boolean>
        get() = _goAddAdmin

    private val _goAddProduct: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val goAddProduct: LiveData<Boolean>
        get() = _goAddProduct

    private val _logout: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean>
        get() = _logout


    fun goAllUser() {
        Log.i("go AllUser", goAllUser.toString())
        _goAllUser.value = true
    }

    fun goAllUserCompleted() {
        _goAllUser.value = false
    }

    fun goAddProduct() {
        _goAddProduct.value = true
    }

    fun goAddProductCompleted() {
        _goAddProduct.value = false
    }

    fun goAddAdmin() {
        _goAddAdmin.value = true
    }

    fun goAddAdminCompleted() {
        _goAddAdmin.value = false
    }

    fun logout() {
        _logout.value = true
    }

    fun logoutCompleted() {
        _logout.value = false
    }

}