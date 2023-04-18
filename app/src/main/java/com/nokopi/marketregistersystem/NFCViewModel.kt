package com.nokopi.marketregistersystem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokopi.marketregistersystem.data.UserDatabaseDao
import kotlinx.coroutines.launch

class NFCViewModel(private val database: UserDatabaseDao): ViewModel() {

    private val _isNewUser = MutableLiveData<Boolean>()
    val isNewUser: LiveData<Boolean>
        get() = _isNewUser

    private val _goAdminSignIn = MutableLiveData<Boolean>()
    val goAdminSignIn: LiveData<Boolean>
        get() = _goAdminSignIn

    fun getUserIdResult(inputId: String) {
        viewModelScope.launch {
            val getId = getUserId(inputId)
            Log.i("nfc2", inputId)
//            Log.i("getId in scope", getId)
            Log.i("Bool", isNewUser.value.toString())
            _isNewUser.value = getId == inputId
        }
    }

    private suspend fun getUserId(inputId: String): String {
        return database.getUserId(inputId)
    }

    fun goSignIn() {
        _goAdminSignIn.value = true
    }

    fun goSignInComplete() {
        _goAdminSignIn.value = false
    }

}