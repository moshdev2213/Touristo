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

class UserRegister : AppCompatActivity() {
    private lateinit var etURegEmail: EditText
    private lateinit var etURegPass: EditText
    private lateinit var etURegRePass: EditText
    private lateinit var etURegTel: EditText
    private lateinit var tvURegSignIn: TextView
    private lateinit var tvURegCpy: TextView
    private lateinit var btnURegSignUp: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)

        // In Activity's onCreate() for instance this transparents the background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        //code starts here
        tvURegSignIn = findViewById(R.id.tvURegSignIn)
        tvURegSignIn.setOnClickListener {
            startActivity(Intent(this@UserRegister,UserLogin::class.java))
            finish()
        }

    }
}