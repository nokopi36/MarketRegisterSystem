package com.nokopi.marketregistersystem.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokopi.marketregistersystem.data.UserDatabaseDao
import kotlinx.coroutines.launch

class UserViewModel(private val database: UserDatabaseDao, private val inputId: String) : ViewModel() {

    private val _balance: MutableLiveData<Int> = MutableLiveData<Int>()
    val balance: LiveData<Int>
        get() = _balance

    private val _userName: MutableLiveData<String> = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    private val _logout: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean>
        get() = _logout

    private val _goPurchase: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val goPurchase: LiveData<Boolean>
        get() = _goPurchase

    fun getUser(inputId: String) {
        viewModelScope.launch {
            val user = database.get(inputId)
            _balance.value = user.userBalance
            _userName.value = user.userName
        }
    }

    fun logout() {
        _logout.value = true
    }

    fun logoutCompleted() {
        _logout.value = false
    }

    fun goPurchase() {
        _goPurchase.value = true
    }

    fun goPurchaseCompleted() {
        _goPurchase.value = false
    }

}