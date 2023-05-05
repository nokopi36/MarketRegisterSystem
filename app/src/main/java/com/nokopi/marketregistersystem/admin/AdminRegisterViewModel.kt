package com.nokopi.marketregistersystem.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokopi.marketregistersystem.data.Admin
import com.nokopi.marketregistersystem.data.AdminDatabaseDao
import kotlinx.coroutines.launch

class AdminRegisterViewModel(private val database: AdminDatabaseDao) : ViewModel() {

    val userNameText: MutableLiveData<String> = MutableLiveData<String>("")
    private val userName: LiveData<String>
        get() = userNameText

    val passwordText: MutableLiveData<String> = MutableLiveData<String>("")
    private val password: LiveData<String>
        get() = passwordText

    private val _goNFCActivity: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val goNFCActivity: LiveData<Boolean>
        get() = _goNFCActivity

    private val _isEnabled: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val isEnabled: LiveData<Boolean>
        get() = _isEnabled

    fun registerAdmin() {
        val inputPassword = password.value.toString()
        viewModelScope.launch {
            val pass = database.getPassword(inputPassword)
            if (pass != inputPassword) {
                _goNFCActivity.value = true
                val adminInfo = Admin(
                    adminName = userName.value.toString(),
                    password = inputPassword
                )
                database.insert(adminInfo)
            } else {
                _goNFCActivity.value = false
            }
        }
    }

    fun updateButton() {
        val isBlank: Boolean = password.value.isNullOrBlank() || userName.value.isNullOrBlank()
        Log.i("isBlank", isBlank.toString())
        _isEnabled.value = !isBlank
    }

}