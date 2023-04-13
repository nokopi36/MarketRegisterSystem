package com.nokopi.marketregistersystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onBackPressedDispatcher.addCallback(callback)
        val getUserResult = intent.getBooleanExtra("getUserResult", false)
        Log.i("getUserResult", getUserResult.toString())
        if (savedInstanceState == null) {
            if (getUserResult) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, UserFragment())
                    .commitNow()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SignUpFragment())
                    .commitNow()
            }
        }
    }

    private val callback = object : OnBackPressedCallback(true) {
        //コールバックのhandleOnBackPressedを呼び出して、戻るキーを押したときの処理を記述
        override fun handleOnBackPressed() {
            // do nothing
            return
        }
    }

}