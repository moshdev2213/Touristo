package com.example.touristo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.ConfirmationDialog
import com.example.touristo.formData.TouristMRegisterValidation
import com.example.touristo.formData.UserProfileValidation
import com.example.touristo.modal.User
import com.example.touristo.repository.UserRepository
import com.example.touristo.validations.ValidationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Timestamp

class TouristManagementRegistration : AppCompatActivity() {
    private  var count=0
    private lateinit var btnTMregistrationCancel:Button
    private lateinit var btnTMRAddT:Button

    private lateinit var simgTMreg:ImageView
    private lateinit var openerImg:ImageView

    private lateinit var btnTMRCurrentCount:Button

    private lateinit var etTMRUserEmail:EditText
    private lateinit var etTMRUSErName:EditText
    private lateinit var etTMRUserPass:EditText
    private lateinit var etTMRUserRePass:EditText
    private lateinit var etTMRUserTel:EditText
    private lateinit var etTMRUserCountry:EditText
    private lateinit var etTMRUserAge:EditText

    private lateinit var imgTMRAddImage:ImageView

    private lateinit var spTMEUserGender:Spinner

    private lateinit var confirmationDialog : ConfirmationDialog
    private lateinit var aemail:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tourist_management_registration)

        //the if block is executed so that the notification pannel color changes and the Icon of them changes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)

            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        val intent = intent.extras
        aemail = intent?.getString("amail").toString()

        openerImg = findViewById(R.id.openerImg)
        simgTMreg = findViewById(R.id.simgTMreg)
        btnTMregistrationCancel = findViewById(R.id.btnTMregistrationCancel)
        btnTMRAddT = findViewById(R.id.btnTMRAddT)

        btnTMregistrationCancel.setOnClickListener {
            finish()
        }
        openerImg.setOnClickListener {
            finish()
        }
        simgTMreg.setOnClickListener {
            val intent = Intent(this@TouristManagementRegistration,AdminUpdateProfile::class.java)
            startActivity(intent)
        }
        btnTMRAddT.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){
                addNewTourist()
            }
        }
    }

    private suspend fun addNewTourist(){
        btnTMRCurrentCount = findViewById(R.id.btnTMRCurrentCount)
        etTMRUserEmail = findViewById(R.id.etTMRUserEmail)
        etTMRUSErName = findViewById(R.id.etTMRUSErName)
        etTMRUserPass = findViewById(R.id.etTMRUserPass)
        etTMRUserRePass = findViewById(R.id.etTMRUserRePass)
        etTMRUserTel = findViewById(R.id.etTMRUserTel)
        etTMRUserCountry = findViewById(R.id.etTMRUserCountry)
        etTMRUserAge = findViewById(R.id.etTMRUserAge)
        imgTMRAddImage = findViewById(R.id.imgTMRAddImage)
        spTMEUserGender = findViewById(R.id.spTMEUserGender)

        val username  = etTMRUSErName.text.toString()
        val userEmail  = etTMRUserEmail.text.toString()
        val userPass  = etTMRUserPass.text.toString()
        val userRepass  = etTMRUserRePass.text.toString()
        val userTEl  = etTMRUserTel.text.toString()
        val userCountry  = etTMRUserCountry.text.toString()
        var userAge  = etTMRUserAge.text.toString()
        val userGender  = spTMEUserGender.selectedItem.toString()

        if(userAge.isEmpty()){
            userAge = "0"
        }
        val userEditForm = TouristMRegisterValidation(
            username,
            userEmail,
            userPass,
            userRepass,
            userTEl,
            userAge.toInt(),
            userCountry
        )

        val validationAge =userEditForm.validationAge()
        val nameValidation =userEditForm.validateUserName()
        val passwordValidation =userEditForm.validatePassword()
        val telValidation =userEditForm.validateTel()
        val validateCountry =userEditForm.validateCountry()
        val validateUserEmail =userEditForm.validateUserEmail()

        when(validateUserEmail){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etTMRUserEmail.error =validateUserEmail.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etTMRUserEmail.error =validateUserEmail.errorMessage
                }

            }
        }
        when(validationAge){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etTMRUserAge.error =validationAge.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etTMRUserAge.error =validationAge.errorMessage
                }

            }
        }

        when(nameValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etTMRUSErName.error =nameValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etTMRUSErName.error =nameValidation.errorMessage
                }

            }
        }

        when(passwordValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etTMRUserPass.error =passwordValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etTMRUserPass.error =passwordValidation.errorMessage
                }

            }
        }

        when(telValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etTMRUserTel.error =telValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etTMRUserTel.error =telValidation.errorMessage
                }

            }
        }
        when(validateCountry){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etTMRUserCountry.error =validateCountry.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etTMRUserCountry.error =validateCountry.errorMessage
                }

            }
        }

        if(count==6){
            //initializing db Credentils and storing data
            val currentDateTime = Timestamp(System.currentTimeMillis())

            // Get an instance of the TouristoDB database
            val db = TouristoDB.getInstance(application)

            // Get the UserDao from the database
            val userDao = db.userDao()
            val userRepo = UserRepository(userDao, Dispatchers.IO)

            if(userRepo.userExist(userEmail)>0){
                lifecycleScope.launch(Dispatchers.Main) {
                    //initializing the dialogBox Class
                    confirmationDialog = ConfirmationDialog(this@TouristManagementRegistration)
                    //lamdas starts here
                    confirmationDialog.dialogWithInfo("Account Already Exists") {
                        //do anything
                    }
                }
            }else{
                lifecycleScope.launch(Dispatchers.Main) {
                    userRepo.insertUser(User(0,username,userEmail,userPass,null,userTEl,userAge.toInt(),userGender,userCountry,currentDateTime.toString(),"",0))
                    //initializing the dialogBox Class
                    confirmationDialog = ConfirmationDialog(this@TouristManagementRegistration)
                    confirmationDialog.dialogWithSuccess("You have successfully registered") {
                        val intent = Intent(this@TouristManagementRegistration, AdminHome::class.java).apply {
                            putExtra("replaceFragment", "TouristManagement")
                            putExtra("adminEmail", aemail)
                        }
                        startActivity(intent)
                        finish()
                    }
                }
            }
            count=0
        }
        count=0
    }
}