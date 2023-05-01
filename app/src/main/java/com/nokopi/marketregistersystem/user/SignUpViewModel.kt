package com.nokopi.marketregistersystem.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokopi.marketregistersystem.data.User
import com.nokopi.marketregistersystem.data.UserDatabaseDao
import kotlinx.coroutines.launch

class SignUpViewModel(private val database: UserDatabaseDao, private val inputId: String): ViewModel() {

    val userNameText: MutableLiveData<String> = MutableLiveData<String>("")
    private val userName: LiveData<String>
        get() = userNameText

    private val _isEnabled: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val isEnabled: LiveData<Boolean>
        get() = _isEnabled

    private val _finishSignUp: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val finishSignUp: LiveData<Boolean>
        get() = _finishSignUp


    fun signUpUser() {
        val inputUserName = userNameText.value.toString()
        viewModelScope.launch {
            val user = User(
                userName = inputUserName,
                userId = inputId,
                userBalance = 0
            )
            database.insert(user)
            finishSignUp()
        }
        Log.i("userName", userName.value.toString())
        Log.i("userId", inputId)
    }

    fun updateButton() {
        val isBlank = userName.value.isNullOrBlank()
        Log.i("isBlank", isBlank.toString())
        _isEnabled.value = !isBlank
    }

    private fun finishSignUp() {
        _finishSignUp.value = true
    }

    fun finishSignUpComplete() {
        _finishSignUp.value = false
    }

}