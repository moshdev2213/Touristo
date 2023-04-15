package com.example.touristo

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.lifecycle.ViewModelProvider
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.modal.User
import com.example.touristo.viewModal.UserViewModal
import com.example.touristo.viewModalProvider.UserViewModalFactory

class MainActivity : AppCompatActivity() {
    private lateinit var userViewModal:UserViewModal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        window.decorView.apply {
//            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//        }

        // Get an instance of the TouristoDB database
        val db = TouristoDB.getInstance(application)

        // Get the UserDao from the database
        val userDao = db.userDao()
        val userFactory = UserViewModalFactory(userDao)
        userViewModal = ViewModelProvider(this,userFactory)[UserViewModal::class.java]

//        userViewModal.insertUser(User(0,"Supun","supun@gmail.com","S!das223","supun.jpg","07133232223",56,"male","srilanka",32424242,78678678))
        showCustomDialogWithAutoLayoutHeight(this@MainActivity)



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