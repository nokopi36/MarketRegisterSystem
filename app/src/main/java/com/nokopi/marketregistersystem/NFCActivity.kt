package com.nokopi.marketregistersystem

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.AppLaunchChecker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.admin.AdminRegisterActivity
import com.nokopi.marketregistersystem.admin.AdminSignInActivity
import com.nokopi.marketregistersystem.data.UserDatabase
import com.nokopi.marketregistersystem.databinding.ActivityNfcBinding

class NFCActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNfcBinding
    private lateinit var  viewModel: NFCViewModel
    private val nfcReader = NFCReader(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nfc)
        val dataSource = UserDatabase.getInstance(this).userDatabaseDao
        val viewModelFactory = NFCViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[NFCViewModel::class.java]
        binding.nfcViewModel = viewModel
        binding.lifecycleOwner = this
        nfcReader.setListener(nfcReaderListener)

        val initStartUp = AppLaunchChecker.hasStartedFromLauncher(this)
        if (!initStartUp) {
            AppLaunchChecker.onActivityCreate(this)
            val adminIntent = Intent(this, AdminRegisterActivity::class.java)
            startActivity(adminIntent)
        }

        viewModel.goAdminSignIn.observe(this) {
            if (it) {
                val adminSignInIntent = Intent(this, AdminSignInActivity::class.java)
                startActivity(adminSignInIntent)
                viewModel.goSignInComplete()
            }
        }


    }

    private val nfcReaderListener = object : NFCReaderInterface{
        override fun onReadTag(tag: Tag) {
            val idm = tag.id
            tag.techList
            val mainActivityIntent = Intent(this@NFCActivity, MainActivity::class.java)
            mainActivityIntent.putExtra("inputId", byteToHex(idm))
            startActivity(mainActivityIntent)
            Log.i("onReadTag", byteToHex(idm))
        }

        override fun onConnect() {
            Log.i("nfc","onConnected")
        }
    }

    private fun byteToHex(b : ByteArray) : String{
        var s = ""
        for (element in b){
            s += "[%02X]".format(element)
        }
        return s
    }

    interface NFCReaderInterface : NFCReader.Listener {
        fun onReadTag(tag: Tag)
        fun onConnect()
    }

    override fun onResume() {
        super.onResume()
        Log.i("Resume", "onResume")
        nfcReader.start()
    }

    override fun onPause() {
        super.onPause()
        Log.i("Pause","onPause")
        nfcReader.stop()
    }

}