package com.example.touristo

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.touristo.modal.User
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EditProfile : AppCompatActivity() {
    private lateinit var btnEditProfileUpdate:Button
    private lateinit var etEditProfileCountry:EditText
    private lateinit var etEditProfileAge:EditText
    private lateinit var etEditProfileGender:EditText
    private lateinit var etEditProfileTel:EditText
    private lateinit var etEditProfilePassword:EditText
    private lateinit var etEditProfileEmail:EditText
    private lateinit var imgShapeEditProfile:ImageView
    private lateinit var tvEditProfileUName:TextView
    private lateinit var tvEditProfileEmail:TextView
    private lateinit var fbEditProfileBtnPencil:FloatingActionButton
    private lateinit var fbEditProfileBtn:FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
// In Activity's onCreate() for instance this transparents the background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        //initialoiozing the views
        etEditProfileCountry=findViewById(R.id.etEditProfileCountry)
        etEditProfileEmail=findViewById(R.id.etEditProfileEmail)
        etEditProfilePassword=findViewById(R.id.etEditProfilePassword)
        etEditProfileTel=findViewById(R.id.etEditProfileTel)
        etEditProfileGender=findViewById(R.id.etEditProfileGender)
        etEditProfileAge=findViewById(R.id.etEditProfileAge)

        tvEditProfileEmail=findViewById(R.id.tvEditProfileEmail)
        tvEditProfileUName=findViewById(R.id.tvEditProfileUName)

        val bundle = intent.extras
        val user = bundle?.getSerializable("user") as? User
        if (user != null) {

            etEditProfileCountry.setText(user.country)
            user.age?.let { etEditProfileAge.setText(it) }
            etEditProfileGender.setText(user.gender)
            etEditProfileTel.setText(user.tel)
            etEditProfilePassword.setText(user.password)

            etEditProfileEmail.setText(user.uemail)
            etEditProfileEmail.isEnabled = false

            tvEditProfileEmail.text = user.uemail
            tvEditProfileUName.text = user.uname
        }
    }
}