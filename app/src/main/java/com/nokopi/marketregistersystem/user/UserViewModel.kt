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

    fun getUser(inputId: String) {
        viewModelScope.launch {
            val user = database.get(inputId)
            _balance.value = user?.userBalance ?: -1
        }
    }

}