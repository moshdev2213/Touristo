package com.example.touristo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.ConfirmationDialog
import com.example.touristo.formData.UserRegisterForm
import com.example.touristo.modal.User
import com.example.touristo.repository.UserRepository
import com.example.touristo.validations.ValidationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Timestamp

class UserRegister : AppCompatActivity() {
    private lateinit var etURegEmail: EditText
    private lateinit var etURegPass: EditText
    private lateinit var etURegRePass: EditText
    private lateinit var etURegTel: EditText
    private lateinit var etURegName: EditText
    private lateinit var tvURegSignIn: TextView
    private lateinit var tvURegNamelabel: TextView
    private lateinit var btnURegSignUp: Button
    private var count = 0;
    private lateinit var confirmationDialog: ConfirmationDialog

    @SuppressLint("ClickableViewAccessibility", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)
        // In Activity's onCreate() for instance this transparents the background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        //code starts here
        etURegName = findViewById(R.id.etURegName)
        etURegTel = findViewById(R.id.etURegTel)
        etURegRePass = findViewById(R.id.etURegRePass)
        etURegPass = findViewById(R.id.etURegPass)
        etURegEmail = findViewById(R.id.etURegEmail)
        tvURegNamelabel = findViewById(R.id.tvURegNamelabel)
        btnURegSignUp = findViewById(R.id.btnURegSignUp)
        tvURegSignIn = findViewById(R.id.tvURegSignIn)

        btnURegSignUp.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                userRegSubmit()
            }
        }
        tvURegSignIn.setOnClickListener {
            startActivity(Intent(this@UserRegister,UserLogin::class.java))
            finish()
        }
        //password hiding unhiding thing comes here
        etURegPass.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = ContextCompat.getDrawable(this, R.drawable.baseline_key_24)
                if (event.x >= etURegPass.width - etURegPass.paddingEnd - drawableEnd?.intrinsicWidth!!) {
                    if (etURegPass.transformationMethod == PasswordTransformationMethod.getInstance()) {
                        etURegPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    } else {
                        etURegPass.transformationMethod = PasswordTransformationMethod.getInstance()
                    }
                    etURegPass.setSelection(etURegPass.text.length)
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }


        etURegRePass.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = ContextCompat.getDrawable(this, R.drawable.baseline_key_24)
                if (event.x >= etURegRePass.width - etURegRePass.paddingEnd - drawableEnd?.intrinsicWidth!!) {
                    if (etURegRePass.transformationMethod == PasswordTransformationMethod.getInstance()) {
                        etURegRePass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    } else {
                        etURegRePass.transformationMethod = PasswordTransformationMethod.getInstance()
                    }
                    etURegRePass.setSelection(etURegRePass.text.length)
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }

    }
    //function for the userForm Submission
    private suspend fun userRegSubmit(){
        val dbName = etURegName.text.toString()
        val dbEmail = etURegEmail.text.toString()
        val dbPassword =  etURegPass.text.toString()
        val dbRepass =  etURegRePass.text.toString()
        val dbTel = etURegTel.text.toString()
        val dbPropic = ""
        val dbAge = 0
        val dbGender = ""
        val dbCountry = ""


        val userRegForm =UserRegisterForm(
            dbName,
            dbEmail,
            dbPassword,
            dbRepass,
            dbTel,
            dbPropic,
            dbAge,
            dbGender,
            dbCountry
        )
        val emailValidation =userRegForm.validateUserEmail()
        val nameValidation =userRegForm.validateUserName()
        val passwordValidation =userRegForm.validatePassword()
        val telValidation =userRegForm.validateTel()

        when(emailValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etURegEmail.error =emailValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etURegEmail.error =emailValidation.errorMessage
                }

            }
        }

        when(nameValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etURegName.error =nameValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etURegName.error =nameValidation.errorMessage
                }

            }
        }

        when(passwordValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etURegPass.error =passwordValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etURegPass.error =passwordValidation.errorMessage
                }

            }
        }

        when(telValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etURegTel.error =telValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etURegTel.error =telValidation.errorMessage
                }

            }
        }
        if(count==4) {

            //initializing db Credentils and storing data
            val currentDateTime = Timestamp(System.currentTimeMillis())

            // Get an instance of the TouristoDB database
            val db = TouristoDB.getInstance(application)

            // Get the UserDao from the database
            val userDao = db.userDao()
            val userRepo =UserRepository(userDao, Dispatchers.IO)

            if(userRepo.userExist(dbEmail)>0){
                lifecycleScope.launch(Dispatchers.Main) {
                    //initializing the dialogBox Class
                    confirmationDialog = ConfirmationDialog(this@UserRegister)
                    //lamdas starts here
                    confirmationDialog.dialogWithInfo("Account Already Exists") {
                       //do anything
                    }
                }
            }else{
                lifecycleScope.launch(Dispatchers.Main) {
                    userRepo.insertUser(User(0,dbName,dbEmail,dbPassword,null,dbTel,dbAge,null,null,currentDateTime.toString(),"",0))
                    //initializing the dialogBox Class
                    confirmationDialog = ConfirmationDialog(this@UserRegister)
                    confirmationDialog.dialogWithSuccess("You have successfully registered") {
                        startActivity(Intent(this@UserRegister,UserLogin::class.java))
                        finish()
                    }
                }
            }
            count=0;
        }
        count=0;
    }

}