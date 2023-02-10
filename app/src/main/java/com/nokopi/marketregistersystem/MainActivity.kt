package com.nokopi.marketregistersystem

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.nokopi.marketregistersystem.data.UserDataBase
import com.nokopi.marketregistersystem.databinding.ActivityMainBinding
import com.nokopi.marketregistersystem.ui.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()
    private val nfcReader = NFCReader(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        nfcReader.setListener(nfcReaderListener)

        val db = Room.databaseBuilder(
            this,
            UserDataBase::class.java, "user-database"
        ).build()
        val userDao = db.userDao()

    }

    private val nfcReaderListener = object : NFCReaderInterface{
        override fun onReadTag(tag: Tag) {
            val idm = tag.id
            tag.techList
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
        nfcReader.stop()
    }

}