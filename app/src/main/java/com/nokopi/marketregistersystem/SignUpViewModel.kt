package com.nokopi.marketregistersystem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nokopi.marketregistersystem.data.User
import com.nokopi.marketregistersystem.data.UserDatabaseDao

class SignUpViewModel(private val database: UserDatabaseDao): ViewModel() {

    val userNameText: MutableLiveData<String> = MutableLiveData<String>("")
    private val userName: LiveData<String>
        get() = userNameText

    private val _isEnabled: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val isEnabled: LiveData<Boolean>
        get() = _isEnabled


    fun signOnUser() {
        Log.i("userName", userName.value.toString())
    }

    fun updateButton() {
        val isBlank = userName.value.isNullOrBlank()
        Log.i("isBlank", isBlank.toString())
        _isEnabled.value = !isBlank
    }

//    var user: User

    private suspend fun insert(user: User) {
        database.insert(user)
    }

}