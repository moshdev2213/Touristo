package com.example.touristo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.ConfirmationDialog
import com.example.touristo.dialogAlerts.ProgressLoader
import com.example.touristo.modal.LogTime
import com.example.touristo.repository.AdminRepository
import com.example.touristo.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Timestamp

class AdminLogin : AppCompatActivity() {
    private lateinit var etAloginEmail: EditText
    private lateinit var etAloginPass:EditText
    private lateinit var tvAloginFP: TextView
    private lateinit var tvAloginUlogin:TextView
    private lateinit var btnAlogin: Button
    private lateinit var progressLoader: ProgressLoader
    private lateinit var confirmationDialog: ConfirmationDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        // In Activity's onCreate() for instance this transparents the background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        //code starts here
        etAloginEmail = findViewById(R.id.etAloginEmail)
        etAloginPass = findViewById(R.id.etAloginPass)
        tvAloginUlogin = findViewById(R.id.tvAloginUlogin)
        btnAlogin = findViewById(R.id.btnAlogin)


        tvAloginUlogin.setOnClickListener {
            startActivity(Intent(this@AdminLogin,UserLogin::class.java))
            finish()
        }
        btnAlogin.setOnClickListener {
            val email = etAloginEmail.text.toString()
            val password = etAloginPass.text.toString()
            println(email+" passsword : "+password)
            if(email.isNotEmpty() && password.isNotEmpty()){
                lifecycleScope.launch(Dispatchers.IO){
                    adminLogin(email,password)
                }
            }else if(email.isEmpty()){
                etAloginEmail.error = "Enter Email"
            }else if(password.isEmpty()){
                etAloginPass.error = "Enter Password"
            }else{
                etAloginEmail.error = "Enter Email"
                etAloginPass.error = "Enter Password"
            }
        }

    }

    private suspend fun adminLogin(email: String, password: String) {
        val currentDateTime = Timestamp(System.currentTimeMillis())
        lifecycleScope.launch(Dispatchers.IO){
            // Get an instance of the TouristoDB database
            val db = TouristoDB.getInstance(application)

            // Get the UserDao from the database
            val adminDao = db.adminDao()
            val adminRepo = AdminRepository(adminDao, Dispatchers.IO)
            val existChecker :Int = adminRepo.getUserLogin(email,password)

            lifecycleScope.launch(Dispatchers.Main){

                if(existChecker==1){
                    progressLoader = ProgressLoader(
                        this@AdminLogin,"Logging In","Please Wait..."
                    )
                    progressLoader.startProgressLoader()

                    adminRepo.insertLoggedTime(LogTime(0,email,"admin",currentDateTime.toString()))
                    val intent = Intent(this@AdminLogin,AdminHome::class.java)
                    intent.putExtra("adminEmail", email)

                    delay(3000L) // delay for 5 seconds
                    progressLoader.dismissProgressLoader() // dismiss the dialog
                    startActivity(intent)
                    finish()
                }else{
                    //initializing the dialogBox Class
                    confirmationDialog = ConfirmationDialog(this@AdminLogin)
                    //lamdas starts here
                    confirmationDialog.dialogWithError("Invalid Credentials") {
                        etAloginPass.text.clear()
                        etAloginEmail.text.clear()
                    }
                }
            }

        }

    }
}