package com.nokopi.marketregistersystem.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.NFCActivity
import com.nokopi.marketregistersystem.R
import com.nokopi.marketregistersystem.data.AdminDatabase
import com.nokopi.marketregistersystem.databinding.ActivityAdminRegisterBinding

class AdminRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminRegisterBinding
    private lateinit var viewModel: AdminRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_register)
        val dataSource = AdminDatabase.getInstance(this).adminDatabaseDao
        val viewModelFactory = AdminRegisterViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[AdminRegisterViewModel::class.java]
        binding.adminRegisterViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.goNFCActivity.observe(this) {
            Log.i("goNFC", it.toString())
            if (it) {
                val nfcIntent = Intent(this, NFCActivity::class.java)
                startActivity(nfcIntent)
            }
        }

    }
}