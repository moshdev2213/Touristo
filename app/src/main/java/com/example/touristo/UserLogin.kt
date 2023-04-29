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
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.ConfirmationDialog
import com.example.touristo.dialogAlerts.ProgressLoader
import com.example.touristo.modal.LogTime
import com.example.touristo.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.sql.Timestamp

class UserLogin : AppCompatActivity() {
    private lateinit var etUloginEmail:EditText
    private lateinit var etUloginPassword:EditText
    private lateinit var tvUloginForgotP:TextView
    private lateinit var tvUloginRegister:TextView
    private lateinit var tvUloginAdmin:TextView
    private lateinit var btnUloginSignIn:Button
    private lateinit var progressLoader: ProgressLoader
    private lateinit var confirmationDialog: ConfirmationDialog
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
            val email = etUloginEmail.text.toString()
            val password = etUloginPassword.text.toString()
            GlobalScope.launch(Dispatchers.IO) {
               userLogins(email,password)
            }
        }
    }

    suspend fun userLogins(email:String,password:String){
        val currentDateTime = Timestamp(System.currentTimeMillis())
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
                       progressLoader = ProgressLoader(
                           this@UserLogin,"Logging In","Please Wait..."
                       )

                        progressLoader.startProgressLoader()

                        userRepo.insertLoggedTime(LogTime(0,email,"user",currentDateTime.toString()))
                        val intent = Intent(this@UserLogin,UserIndex::class.java)
                        intent.putExtra("useremail", email)

                        delay(3000L) // delay for 5 seconds
                        progressLoader.dismissProgressLoader() // dismiss the dialog
                        startActivity(intent)
                        finish()

                    }
                }else{
                    GlobalScope.launch(Dispatchers.Main) {
                        //initializing the dialogBox Class
                        confirmationDialog = ConfirmationDialog(this@UserLogin)
                        //lamdas starts here
                        confirmationDialog.dialogWithError("Invalid Credentials") {
                            etUloginPassword.text.clear()
                            etUloginEmail.text.clear()
                        }
                    }
                }
            }
        }else if(email.isEmpty()){
            GlobalScope.launch(Dispatchers.Main){
                etUloginEmail.error="Enter Email"
            }
        }else if(password.isEmpty()){
            GlobalScope.launch(Dispatchers.Main){
                etUloginPassword.error="Enter Password"
            }
        }
    }
}