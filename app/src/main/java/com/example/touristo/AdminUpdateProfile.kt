package com.example.touristo

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.content.ContextCompat
import com.example.touristo.modal.Admin
import com.example.touristo.modal.User
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AdminUpdateProfile : AppCompatActivity() {
    private lateinit var aemail:String

    private lateinit var btnAdminProfileCancel:Button
    private lateinit var btnAdminProfileSubmit:Button
    private lateinit var etAdminProfileEditAge:EditText
    private lateinit var etAdminEditProfileDesignation:Spinner
    private lateinit var spAdmniEditProfileGender:Spinner
    private lateinit var etAdminEditProfileNumber:EditText
    private lateinit var etAdminEditProfileLname:EditText
    private lateinit var etAdminEditProfileFname:EditText
    private lateinit var etAdminProfilePassword:EditText
    private lateinit var etAdminEditProfileEail:EditText
    private lateinit var fbAdminEditProfilePencil:FloatingActionButton
    private lateinit var simgAdminProfileUpdatePic:ImageView
    private lateinit var openerImg:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_update_profile)

        //the if block is executed so that the notification pannel color changes and the Icon of them changes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.bgBackground)

            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        //iniitialize the views
        btnAdminProfileCancel = findViewById(R.id.btnAdminProfileCancel)
        btnAdminProfileSubmit = findViewById(R.id.btnAdminProfileSubmit)
        etAdminProfileEditAge = findViewById(R.id.etAdminProfileEditAge)
        etAdminEditProfileDesignation = findViewById(R.id.etAdminEditProfileDesignation)
        spAdmniEditProfileGender = findViewById(R.id.spAdmniEditProfileGender)
        etAdminEditProfileNumber = findViewById(R.id.etAdminEditProfileNumber)
        etAdminEditProfileLname = findViewById(R.id.etAdminEditProfileLname)
        etAdminEditProfileFname = findViewById(R.id.etAdminEditProfileFname)
        etAdminProfilePassword = findViewById(R.id.etAdminProfilePassword)
        etAdminEditProfileEail = findViewById(R.id.etAdminEditProfileEail)
        fbAdminEditProfilePencil = findViewById(R.id.fbAdminEditProfilePencil)
        simgAdminProfileUpdatePic = findViewById(R.id.simgAdminProfileUpdatePic)
        openerImg = findViewById(R.id.openerImg)

        etAdminEditProfileEail.isEnabled = false
        openerImg.setOnClickListener {
            finish()
        }
        btnAdminProfileCancel.setOnClickListener{
            finish()
        }

        val bundle = intent.extras
        val admin = bundle?.getSerializable("admin") as? Admin
        aemail = bundle?.getString("amail").toString()
        if(admin!=null){

        }
    }
}