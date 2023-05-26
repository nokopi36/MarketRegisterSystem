package com.nokopi.marketregistersystem.user

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.nokopi.marketregistersystem.NFCActivity
import com.nokopi.marketregistersystem.R
import kotlinx.coroutines.Runnable

class UserActivity : AppCompatActivity() {

    companion object {
        const val INTERVAL_MILLISECOND : Long = 60000L
    }

    private val handler = Handler(Looper.getMainLooper())
    private val timeOutTimer = Runnable {
        AlertDialog.Builder(this@UserActivity)
            .setTitle("タイムアウト")
            .setMessage("一定時間操作がなかったためタイムアウトしました。")
            .setPositiveButton("OK") { _, _ ->
                val nfcIntent = Intent(this@UserActivity, NFCActivity::class.java)
                startActivity(nfcIntent)
            }
            .setCancelable(false)
            .show()
    }

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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i("touch", "onTouchEvent")
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.i("touch", "DOWN")
                handler.removeCallbacks(timeOutTimer)
                handler.postDelayed(timeOutTimer, INTERVAL_MILLISECOND)
            }
        }

        return super.onTouchEvent(event)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(timeOutTimer, INTERVAL_MILLISECOND)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(timeOutTimer)
    }

}