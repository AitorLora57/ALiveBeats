package com.example.alivebeats

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.alivebeats.activities.LoginActivity

class Splash_Screen : AppCompatActivity() {

    private val time : Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screan)
        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }, time)

    }
}