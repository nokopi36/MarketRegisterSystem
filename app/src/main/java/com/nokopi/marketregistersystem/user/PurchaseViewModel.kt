package com.nokopi.marketregistersystem.user

import android.app.Application
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokopi.marketregistersystem.R
import com.nokopi.marketregistersystem.data.Product
import com.nokopi.marketregistersystem.data.ProductDatabaseDao
import com.nokopi.marketregistersystem.data.User
import com.nokopi.marketregistersystem.data.UserDatabaseDao
import kotlinx.coroutines.launch

class PurchaseViewModel(
    private val userDatabase: UserDatabaseDao,
    productDatabase: ProductDatabaseDao,
    private val inputId: String,
    application: Application
) : ViewModel() {

    var productList: LiveData<List<Product>> = productDatabase.get()

    private val _purchaseResult: MutableLiveData<Int> = MutableLiveData<Int>(0)
    val purchaseResult: LiveData<Int>
        get() = _purchaseResult

    private val _finishPurchase: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val finishPurchase: LiveData<Boolean>
        get() = _finishPurchase

    private val _canPurchase: MutableLiveData<Int> = MutableLiveData<Int>()
    val canPurchase: LiveData<Int>
        get() = _canPurchase

    private val audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
    private val sound = SoundPool.Builder().setMaxStreams(1).setAudioAttributes(audioAttributes).build()
    private val cashSound = sound.load(application, R.raw.cash, 1)

    fun isAbleToPurchase() {
        if (_purchaseResult.value == 0) {
            _canPurchase.value = 0
        } else {
            viewModelScope.launch {
                val user = userDatabase.get(inputId)
                val result = (user.userBalance) - _purchaseResult.value!!
                if (result >= 0) {
                    _canPurchase.value = 1
                } else {
                    _canPurchase.value = -1
                }
            }
        }
    }

    fun purchase() {
        viewModelScope.launch {
            val user = userDatabase.get(inputId)
            val result = user.userBalance - _purchaseResult.value!!
            val updateUser = User(
                user.id, user.userId,
                user.userName, result
            )
            userDatabase.update(updateUser)
            sound.play(cashSound, 1.0f, 1.0f, 0, 0, 1.0f)
            finishPurchase()
        }
    }

    fun addPurchaseResult(product: Product) {
        _purchaseResult.value = _purchaseResult.value?.plus(product.productPrice)
    }

    fun reset() {
        _purchaseResult.value = 0
    }

    fun finishPurchase() {
        _finishPurchase.value = true
    }

    fun finishPurchaseComplete() {
        _finishPurchase.value = false
    }

}