package com.example.touristo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.touristo.modal.User
import com.example.touristo.modal.Villa

class Thanku : AppCompatActivity() {
    private lateinit var btnSeeBooking:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thanku)
        val bundle = intent.extras
        val villa = bundle?.getSerializable("villa") as? Villa
        val user = bundle?.getSerializable("user") as? User

        btnSeeBooking = findViewById(R.id.btnSeeBooking)
        btnSeeBooking.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("villa", villa)
                putSerializable("user", user)
            }
            val intent = Intent(this@Thanku, UserIndex::class.java)
            intent.putExtras(bundle)
            intent.putExtra("useremail", user?.uemail)
            startActivity(intent)
            finish()
        }

    }
}