package com.nokopi.marketregistersystem.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nokopi.marketregistersystem.R

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AdminFragment.newInstance())
                .commitNow()
        }
    }
}