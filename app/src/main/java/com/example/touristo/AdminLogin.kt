package com.example.touristo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat

class AdminLogin : AppCompatActivity() {
    private lateinit var etAloginEmail: EditText
    private lateinit var etAloginPass:EditText
    private lateinit var tvAloginFP: TextView
    private lateinit var tvAloginUlogin:TextView
    private lateinit var btnAlogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        // In Activity's onCreate() for instance this transparents the background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        //code starts here
        tvAloginUlogin = findViewById(R.id.tvAloginUlogin)
        tvAloginUlogin.setOnClickListener {
            startActivity(Intent(this@AdminLogin,UserLogin::class.java))
            finish()
        }

    }
}