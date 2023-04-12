package com.nokopi.marketregistersystem

import MainViewModelFactory
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.data.UserDatabase
import com.nokopi.marketregistersystem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  viewModel: MainViewModel
    private val nfcReader = NFCReader(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dataSource = UserDatabase.getInstance(this).userDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this
        nfcReader.setListener(nfcReaderListener)


    }

    private val nfcReaderListener = object : NFCReaderInterface{
        override fun onReadTag(tag: Tag) {
            val idm = tag.id
            tag.techList
            if (viewModel.getUserId(byteToHex(idm))) {
                val userFragment = UserFragment()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(R.id.fragmentView, userFragment)
                transaction.commit()
            } else {
                val signUpFragment = SignUpFragment()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(R.id.fragmentView, signUpFragment)
                transaction.commit()
            }
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
        nfcReader.start()
    }

    override fun onPause() {
        super.onPause()
        Log.i("Pause","onPause")
        nfcReader.stop()
    }

}