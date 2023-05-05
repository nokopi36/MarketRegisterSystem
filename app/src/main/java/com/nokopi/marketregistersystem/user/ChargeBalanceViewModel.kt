package com.nokopi.marketregistersystem.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokopi.marketregistersystem.data.User
import com.nokopi.marketregistersystem.data.UserDatabaseDao
import kotlinx.coroutines.launch

class ChargeBalanceViewModel(
    private val database: UserDatabaseDao,
    private val inputId: String
) : ViewModel()  {

    private val _finishCharge: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val finishCharge: LiveData<Boolean>
        get() = _finishCharge

    private val _chargeResult: MutableLiveData<Int> = MutableLiveData<Int>(0)
    val chargeResult: LiveData<Int>
        get() = _chargeResult

    private val _canCharge: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val canCharge: LiveData<Boolean>
        get() = _canCharge

    fun canCharge() {
        _canCharge.value = _chargeResult.value != 0
    }

    fun charge() {
        viewModelScope.launch {
            val user = database.get(inputId)
            val updateUser = User(user.id, user.userId, user.userName, user.userBalance + _chargeResult.value!!)
            database.update(updateUser)
            finishCharge()
        }
    }

    fun finishCharge() {
        _finishCharge.value = true
    }

    fun finishChargeComplete() {
        _finishCharge.value = false
    }

    fun addCharge(money: Int) {
        _chargeResult.value = _chargeResult.value?.plus(money)
    }

    fun reset() {
        _chargeResult.value = 0
    }

}