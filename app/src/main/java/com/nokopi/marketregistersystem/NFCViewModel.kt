package com.nokopi.marketregistersystem

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokopi.marketregistersystem.data.UserDatabaseDao
import kotlinx.coroutines.launch

class NFCViewModel(private val database: UserDatabaseDao): ViewModel() {

    fun getUserIdResult(inputId: String): Boolean {
        var getId = ""
        viewModelScope.launch {
            getId = getUserId(inputId)
        }
        Log.i("nfc2", inputId)
        Log.i("getId", getId)
        Log.i("Bool", (getId == inputId).toString())

        return getId == inputId
    }

    private suspend fun getUserId(inputId: String): String {
        return database.getUserId(inputId)
    }

}