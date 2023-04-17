package com.example.touristo

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserLogin : AppCompatActivity() {
    private lateinit var etUloginEmail:EditText
    private lateinit var etUloginPassword:EditText
    private lateinit var tvUloginForgotP:TextView
    private lateinit var tvUloginRegister:TextView
    private lateinit var tvUloginAdmin:TextView
    private lateinit var btnUloginSignIn:Button
    private lateinit var dialog: Dialog
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
        etUloginEmail = findViewById(R.id.etUloginEmail)
        etUloginPassword = findViewById(R.id.etUloginPassword)

        val email = etUloginEmail.toString()
        val password = etUloginPassword.toString()

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
        btnUloginSignIn = findViewById(R.id.btnUloginSignIn)
        btnUloginSignIn.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                println("showing "+email+" "+password)
               userLogins(email,password)
            }
        }
    }
    fun showCustomDialogWithAutoLayoutHeight(context: Context, title :String, description:String) {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_success)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val dgDescription = dialog.findViewById<TextView>(R.id.tvDgDecsrition)
        val dgOkBtn = dialog.findViewById<TextView>(R.id.btnDgOk)
        val imgDg = dialog.findViewById<ImageView>(R.id.imgDg)


        // Get resource ID of image based on title
        val resourceId = context.resources.getIdentifier(title, "drawable", context.packageName)
        // Set image using resource ID
        imgDg.setImageResource(resourceId)
        dgDescription.text = description

        val color:Int
        if(title.equals("error", ignoreCase = true)){
            color = ContextCompat.getColor(this, R.color.bgDialogError)
            dgOkBtn.backgroundTintList = ColorStateList.valueOf(color)

            dgOkBtn.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }
    suspend fun userLogins(email:String,password:String){
        if(email.isNotEmpty() && password.isNotEmpty()){
            GlobalScope.launch(Dispatchers.IO) {
                // Get an instance of the TouristoDB database
                val db = TouristoDB.getInstance(application)

                // Get the UserDao from the database
                val userDao = db.userDao()
                val userRepo = UserRepository(userDao, Dispatchers.IO)
                val existChecker :Int = userRepo.getuserLogin(email,password)

                if(existChecker==1){
                    GlobalScope.launch(Dispatchers.Main) {
                        startActivity(Intent(this@UserLogin,UserIndex::class.java))
                        finish()
                    }

                }else{
                    GlobalScope.launch(Dispatchers.Main) {
                        showCustomDialogWithAutoLayoutHeight(this@UserLogin,"error","Invalid Credentials")
                    }

                }
            }
        }else if(password.isEmpty()){
            etUloginPassword.error="Enter Password"
        }else if(email.isEmpty()){
            etUloginEmail.error="Enter Email"
        }
    }
}