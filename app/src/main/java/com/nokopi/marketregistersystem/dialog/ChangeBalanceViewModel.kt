package com.nokopi.marketregistersystem.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokopi.marketregistersystem.data.User
import com.nokopi.marketregistersystem.data.UserDatabaseDao
import kotlinx.coroutines.launch

class ChangeBalanceViewModel(private val database: UserDatabaseDao, private val user: User) :
    ViewModel() {

    val newBalance: MutableLiveData<String> = MutableLiveData<String>()
    private val _newBalance: LiveData<String>
        get() = newBalance

    fun isBalanceNull(): Boolean {
        return !_newBalance.value.isNullOrBlank()
    }

    fun changeBalance() {
        viewModelScope.launch {
            val user = User(user.id, user.userId, user.userName, newBalance.value?.toInt() ?: -1)
            database.update(user)
        }
    }

}