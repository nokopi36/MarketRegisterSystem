package com.nokopi.marketregistersystem.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokopi.marketregistersystem.data.AdminDatabaseDao
import kotlinx.coroutines.launch

class AdminSignInViewModel(private val database: AdminDatabaseDao): ViewModel() {

    val userNameText: MutableLiveData<String> = MutableLiveData<String>("")
    private val userName: LiveData<String>
        get() = userNameText

    val passwordText: MutableLiveData<String> = MutableLiveData<String>("")
    private val password: LiveData<String>
        get() = passwordText

    private val _isEnabled: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val isEnabled: LiveData<Boolean>
        get() = _isEnabled

    private val _signIn: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val signIn: LiveData<Boolean>
        get() = _signIn


    fun checkSignIn() {
        viewModelScope.launch {
            val admin = database.getAdmin(userName.value.toString())
            Log.i("admin_name", admin?.adminName.toString())
            Log.i("pass", admin?.password.toString())
            _signIn.value =
                (admin?.adminName ?: "no user") == userName.value.toString() && (admin?.password
                    ?: "no pass") == password.value.toString()
        }
    }

    fun updateButton() {
        val isBlank = userName.value.isNullOrBlank() || password.value.isNullOrBlank()
        Log.i("isBlank", isBlank.toString())
        _isEnabled.value = !isBlank
    }

}