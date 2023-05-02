package com.example.touristo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat

class TouristManagementRegistration : AppCompatActivity() {
    private lateinit var btnTMregistrationCancel:Button
    private lateinit var simgTMreg:ImageView
    private lateinit var openerImg:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tourist_management_registration)

        //the if block is executed so that the notification pannel color changes and the Icon of them changes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)

            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        openerImg = findViewById(R.id.openerImg)
        simgTMreg = findViewById(R.id.simgTMreg)
        btnTMregistrationCancel = findViewById(R.id.btnTMregistrationCancel)

        btnTMregistrationCancel.setOnClickListener {
            finish()
        }
        openerImg.setOnClickListener {
            finish()
        }
        simgTMreg.setOnClickListener {
            val intent = Intent(this@TouristManagementRegistration,AdminUpdateProfile::class.java)
            startActivity(intent)
        }
    }
}