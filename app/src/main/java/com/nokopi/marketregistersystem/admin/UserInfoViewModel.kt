package com.nokopi.marketregistersystem.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokopi.marketregistersystem.data.User
import com.nokopi.marketregistersystem.data.UserDatabaseDao
import kotlinx.coroutines.launch

class UserInfoViewModel(private val database: UserDatabaseDao, private val user: User): ViewModel() {

    private var _userInfoName: MutableLiveData<String> = MutableLiveData<String>(user.userName)
    val userInfoName: LiveData<String>
        get() = _userInfoName

    private var _userInfoId: MutableLiveData<String> = MutableLiveData<String>(user.userId)
    val userInfoId: LiveData<String>
        get() = _userInfoId

    private var _userInfoBalance: MutableLiveData<Int> = MutableLiveData<Int>(user.userBalance)
    val userInfoBalance: LiveData<Int>
        get() = _userInfoBalance

    private var _goAdmin: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val goAdmin: LiveData<Boolean>
        get() = _goAdmin

    private var _isClickErase: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isClickErase: LiveData<Boolean>
        get() = _isClickErase

    private var _isClickChaneBalance: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isClickChangeBalance: LiveData<Boolean>
        get() = _isClickChaneBalance

    fun onClickErase() {
        _isClickErase.value = true
    }

    fun noErase() {
        _isClickErase.value = false
    }

    fun onClickChangeBalance() {
        _isClickChaneBalance.value = true
    }

    fun noChangeBalance() {
        _isClickChaneBalance.value = false
    }

    fun updateUserInfo() {
        viewModelScope.launch {
            val updateUser = database.get(user.userId)
            _userInfoName.value = updateUser.userName
            _userInfoId.value = updateUser.userId
            _userInfoBalance.value = updateUser.userBalance
        }
    }

    fun deleteUser() {
        viewModelScope.launch {
            database.delete(user)
            _goAdmin.value = true
        }
    }

    fun goAdminCompleted() {
        _goAdmin.value = false
    }

}