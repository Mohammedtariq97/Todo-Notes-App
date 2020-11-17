package com.example.todonotesappjava.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotesappjava.R
import com.example.todonotesappjava.onboarding.OnBoardingActivity
import com.example.todonotesappjava.data.local.pref.StoreSession
import com.example.todonotesappjava.data.local.pref.prefConstant
import com.example.todonotesappjava.login.LoginActivity
import com.example.todonotesappjava.mynotes.MyNotesActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity(){
    lateinit var handler: Handler
    lateinit var runnable: Runnable
    val TAG = "SplashActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setUpSharedPreferences()
        goToNext()
        getFCMToken()
    }

    private fun goToNext() {
        handler= Handler()
        runnable= Runnable {
            checkLoginStatus()
        }
        handler.postDelayed(runnable,2000)
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            // Log and toast
            if (token != null) {
                Log.d(TAG, token)
            }
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setUpSharedPreferences() {
        StoreSession.init(this)
    }

    private fun checkLoginStatus() {
        val isLoggedIn = StoreSession.read(prefConstant.IS_LOGGED_IN)
        val isBoardingSuccess = StoreSession.read(prefConstant.ON_BOARDED_SUCCESSFULLY)
        if(isLoggedIn!!){
            val intent = Intent(this, MyNotesActivity::class.java)
            startActivity(intent)
        }else{
            if(isBoardingSuccess!!) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, OnBoardingActivity::class.java)
                startActivity(intent)
            }
        }
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        handler.removeCallbacks(runnable)
    }
}