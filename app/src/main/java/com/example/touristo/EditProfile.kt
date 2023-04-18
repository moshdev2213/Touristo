package com.example.touristo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.touristo.modal.User

class EditProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val bundle = intent.extras
        val user = bundle?.getSerializable("user") as? User
        if (user != null) {
            Toast.makeText(this,user.password,Toast.LENGTH_LONG).show()
        }
    }
}