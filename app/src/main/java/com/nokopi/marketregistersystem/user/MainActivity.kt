package com.nokopi.marketregistersystem.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.*
import com.nokopi.marketregistersystem.data.UserDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var  viewModel: NFCViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataSource = UserDatabase.getInstance(this).userDatabaseDao
        val viewModelFactory = NFCViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[NFCViewModel::class.java]

        onBackPressedDispatcher.addCallback(callback)
        val inputId = intent.getStringExtra("inputId")
        val args = Bundle()
        args.putString("inputId", inputId)
        Log.i("getUserResult", inputId.toString())

        viewModel.getUserIdResult(inputId.toString())

        viewModel.isNewUser.observe(this) {
            if (it) {
                Log.i("Bool in main", it.toString())
                val fragment = UserFragment()
                fragment.arguments = args
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow()
            } else {
                Log.i("Bool in main", it.toString())
                val fragment = SignUpFragment()
                fragment.arguments = args
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
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