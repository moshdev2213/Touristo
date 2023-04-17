package com.example.touristo

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.modal.User

class MainActivity : AppCompatActivity() {
    private lateinit var btnMainGo:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        window.decorView.apply {
//            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//        }
        btnMainGo=findViewById(R.id.btnMainLaunch)
        btnMainGo.setOnClickListener {
           val intent = Intent(this@MainActivity, UserLogin::class.java)
           startActivity(intent)
           finish()
        }


    }
    fun showCustomDialogWithAutoLayoutHeight(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_info)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()
    }
}