package com.nokopi.marketregistersystem

import android.app.Activity
import android.content.Context
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
import android.nfc.tech.NfcF
import android.os.Looper
import android.os.Message
import android.util.Log

class NFCReader(private val context: Context, private val activity: Activity): android.os.Handler(Looper.getMainLooper()) {
    private var nfcManager: NfcManager? = null
    private var nfcAdapter : NfcAdapter? = null
    private var callback : CustomReaderCallback? = null

    private var listener: NFCActivity.NFCReaderInterface? = null
    interface Listener

    fun start(){
        callback = CustomReaderCallback()
        callback?.setHandler(this)
        nfcManager = context.getSystemService(Context.NFC_SERVICE) as NfcManager?
        nfcAdapter = nfcManager!!.defaultAdapter
        nfcAdapter!!.enableReaderMode(activity,callback
            ,NfcAdapter.FLAG_READER_NFC_F or
                    NfcAdapter.FLAG_READER_NFC_A or
                    NfcAdapter.FLAG_READER_NFC_B or
                    NfcAdapter.FLAG_READER_NFC_V or
                    NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,null)
    }
    fun stop(){
        nfcAdapter!!.disableReaderMode(activity)
        callback = null
    }

    override fun handleMessage(msg: Message) {                  // コールバックからのメッセージクラス
        if (msg.arg1 == 1){                                     // 読み取り終了
            listener?.onReadTag(msg.obj as Tag)                 // 拡張用
        }
        if (msg.arg1 == 2){                                     // 読み取り終了
            listener?.onConnect()                               // 拡張用
        }
    }

    fun setListener(listener: Listener?) {         // イベント受け取り先を設定
        if (listener is NFCActivity.NFCReaderInterface) {
            this.listener = listener
        }
    }

    private class CustomReaderCallback : NfcAdapter.ReaderCallback {
        private var handler : android.os.Handler? = null
        override fun onTagDiscovered(tag: Tag) {
            Log.d("Sample", tag.id.toString())
            val msg = Message.obtain()
            msg.arg1 = 1
            msg.obj = tag
            if (handler != null) handler?.sendMessage(msg)
            val nfc : NfcF = NfcF.get(tag) ?: return
            try {
                nfc.connect()
                //nfc.transceive()
                nfc.close()
                msg.arg1 = 2
                msg.obj = tag
                if (handler != null) handler?.sendMessage(msg)
            }catch (e : Exception){
                nfc.close()
            }
        }
        fun setHandler(handler  : android.os.Handler){
            this.handler = handler
        }
    }
}