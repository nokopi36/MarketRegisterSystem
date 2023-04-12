package com.nokopi.marketregistersystem

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokopi.marketregistersystem.data.UserDatabaseDao
import kotlinx.coroutines.launch

class MainViewModel(private val database: UserDatabaseDao): ViewModel() {

    fun getUserId(inputId: String): Boolean {
        var getId = ""
        viewModelScope.launch {
            getId = database.getUserId(inputId)
        }
        Log.i("nfc2", inputId)
        Log.i("getId", getId)
        Log.i("Bool", (getId == inputId).toString())

        return getId == inputId
    }

}