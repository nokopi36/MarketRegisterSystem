package com.nokopi.marketregistersystem.admin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokopi.marketregistersystem.data.User
import com.nokopi.marketregistersystem.data.UserDatabaseDao
import kotlinx.coroutines.launch

class AllUserViewModel(private val database: UserDatabaseDao) : ViewModel() {

    private val _userList: MutableLiveData<List<User>> = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>>
        get() = _userList

    private val _pushBackup:MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val pushBackup: LiveData<Boolean>
        get() = _pushBackup

    fun getAllUser() {
        viewModelScope.launch {
            _userList.value = database.getAllUser()
        }
        Log.i("getAllUser","get all users")
    }

    fun pushBackup() {
        Log.i("in pushBackup","yes")
        _pushBackup.value = true
        Log.i("pushbackup",pushBackup.toString())
    }



//    var userList: LiveData<List<User>> = database.getAllUser()



}