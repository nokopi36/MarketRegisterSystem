package com.nokopi.marketregistersystem.admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.R
import com.nokopi.marketregistersystem.data.AdminDatabase
import com.nokopi.marketregistersystem.databinding.ActivityAdminSigninBinding

class AdminSignInActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAdminSigninBinding
    private lateinit var viewModel: AdminSignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_signin)
        val dataSource = AdminDatabase.getInstance(this).adminDatabaseDao
        val viewModelFactory = AdminSignInViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[AdminSignInViewModel::class.java]
        binding.adminSignInViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.signIn.observe(this) {
            if (it) {
                val adminIntent = Intent(this, AdminActivity::class.java)
                startActivity(adminIntent)
            } else {
                AlertDialog.Builder(this)
                    .setTitle("ログイン失敗")
                    .setMessage("ユーザーもしくはパスワードが違います")
                    .setPositiveButton("OK") {_, _ ->}
                    .show()
            }
        }

    }

}