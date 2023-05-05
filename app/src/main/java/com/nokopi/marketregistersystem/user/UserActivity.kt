package com.nokopi.marketregistersystem.user

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.nokopi.marketregistersystem.R

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val inputId = intent.getStringExtra("inputId")
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.user_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val args = UserFragmentArgs(inputId.toString())
        navController.setGraph(R.navigation.user_nav_graph, args.toBundle())

        onBackPressedDispatcher.addCallback(callback)
    }

    private val callback = object : OnBackPressedCallback(true) {
        //コールバックのhandleOnBackPressedを呼び出して、戻るキーを押したときの処理を記述
        override fun handleOnBackPressed() {
            // do nothing
            return
        }
    }

}