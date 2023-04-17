package com.example.touristo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class UserLogin : AppCompatActivity() {
    private lateinit var etUloginEmail:EditText
    private lateinit var etUloginPassword:EditText
    private lateinit var tvUloginForgotP:TextView
    private lateinit var tvUloginRegister:TextView
    private lateinit var tvUloginAdmin:TextView
    private lateinit var btnUloginSignIn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        // In Activity's onCreate() for instance this transparents the background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        //coding stuff starts here
        tvUloginRegister = findViewById(R.id.tvUloginSignup)
        tvUloginRegister.setOnClickListener {
            startActivity(Intent(this@UserLogin,UserRegister::class.java))
            finish()
        }

        tvUloginAdmin = findViewById(R.id.tvUlogAdmin)
        tvUloginAdmin.setOnClickListener {
            startActivity(Intent(this@UserLogin,AdminLogin::class.java))
            finish()
        }
    }
}