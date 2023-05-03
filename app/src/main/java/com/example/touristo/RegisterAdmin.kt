package com.example.touristo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.ConfirmationDialog
import com.example.touristo.formData.AdminRegisterValidation
import com.example.touristo.formData.TouristMRegisterValidation
import com.example.touristo.modal.Admin
import com.example.touristo.modal.User
import com.example.touristo.repository.AdminRepository
import com.example.touristo.repository.UserRepository
import com.example.touristo.validations.ValidationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Timestamp

class RegisterAdmin : AppCompatActivity() {
    private var count=0
    private lateinit var confirmationDialog:ConfirmationDialog
    private lateinit var aemail:String

    private lateinit var etAdminRegisterAge:EditText
    private lateinit var spAdminRegisterDesignation:Spinner
    private lateinit var spAdminRegisterGender:Spinner
    private lateinit var etAdminRegisterTel:EditText
    private lateinit var etAdminRegisterLName:EditText
    private lateinit var etAdminRegisterFName:EditText
    private lateinit var etAdminregisterRePass:EditText
    private lateinit var etAdminRegisterPass:EditText
    private lateinit var etAdminRegisterEmail:EditText
    private lateinit var openerImg:ImageView
    private lateinit var btnAdminRegisterBackBtn:Button
    private lateinit var btnAdminRegisterAddAdmin:Button
    private lateinit var imgAdminRegister:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_admin)

        //the if block is executed so that the notification pannel color changes and the Icon of them changes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.bgBackground)

            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        val intent = intent.extras
        aemail = intent?.getString("amail").toString()

        //initializing the views
        openerImg = findViewById(R.id.openerImg)
        btnAdminRegisterBackBtn = findViewById(R.id.btnAdminRegisterBackBtn)
        btnAdminRegisterAddAdmin = findViewById(R.id.btnAdminRegisterAddAdmin)

        btnAdminRegisterBackBtn.setOnClickListener {
            finish()
        }
        openerImg.setOnClickListener {
            finish()
        }
        btnAdminRegisterAddAdmin.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){
                addNewStaff()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private suspend fun addNewStaff() {
        etAdminRegisterAge = findViewById(R.id.etAdminRegisterAge)
        spAdminRegisterDesignation = findViewById(R.id.spAdminRegisterDesignation)
        spAdminRegisterGender = findViewById(R.id.spAdminRegisterGender)
        etAdminRegisterTel = findViewById(R.id.etAdminRegisterTel)
        etAdminRegisterLName = findViewById(R.id.etAdminRegisterLName)
        etAdminRegisterFName = findViewById(R.id.etAdminRegisterFName)
        etAdminRegisterPass = findViewById(R.id.etAdminRegisterPass)
        etAdminregisterRePass = findViewById(R.id.etAdminregisterRePass)
        etAdminRegisterEmail = findViewById(R.id.etAdminRegisterEmail)
        imgAdminRegister = findViewById(R.id.imgAdminRegister)

        //password hiding unhiding thing comes here
        etAdminregisterRePass.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = ContextCompat.getDrawable(this, R.drawable.baseline_key_24)
                if (event.x >= etAdminregisterRePass.width - etAdminregisterRePass.paddingEnd - drawableEnd?.intrinsicWidth!!) {
                    if (etAdminregisterRePass.transformationMethod == PasswordTransformationMethod.getInstance()) {
                        etAdminregisterRePass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    } else {
                        etAdminregisterRePass.transformationMethod = PasswordTransformationMethod.getInstance()
                    }
                    etAdminregisterRePass.setSelection(etAdminregisterRePass.text.length)
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }


        etAdminRegisterPass.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = ContextCompat.getDrawable(this, R.drawable.baseline_key_24)
                if (event.x >= etAdminRegisterPass.width - etAdminRegisterPass.paddingEnd - drawableEnd?.intrinsicWidth!!) {
                    if (etAdminRegisterPass.transformationMethod == PasswordTransformationMethod.getInstance()) {
                        etAdminRegisterPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    } else {
                        etAdminRegisterPass.transformationMethod = PasswordTransformationMethod.getInstance()
                    }
                    etAdminRegisterPass.setSelection(etAdminRegisterPass.text.length)
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }

        val adminImg  = "propic1"
        val adminEmail  = etAdminRegisterEmail.text.toString()
        val adminFname  = etAdminRegisterFName.text.toString()
        val adminLname  = etAdminRegisterLName.text.toString()
        val adminPass  = etAdminRegisterPass.text.toString()
        val adminRepass  = etAdminregisterRePass.text.toString()
        val adminTEl  = etAdminRegisterTel.text.toString()
        val adminGender  = spAdminRegisterGender.selectedItem.toString()
        var adminAge  = etAdminRegisterAge.text.toString()
        val adminDesignation  = spAdminRegisterDesignation.selectedItem.toString()

        if(adminAge.isEmpty()){
            adminAge = "0"
        }
        val userEditForm = AdminRegisterValidation(
            adminFname,
            adminLname,
            adminEmail,
            adminPass,
            adminRepass,
            adminTEl,
            adminAge
        )

        val validationAge =userEditForm.validationAge()
        val fnameValidation =userEditForm.validateFirstName()
        val lnameValidation =userEditForm.validateLastName()
        val passwordValidation =userEditForm.validatePassword()
        val telValidation =userEditForm.validateTel()
        val validateUserEmail =userEditForm.validateUserEmail()

        when(validateUserEmail){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAdminRegisterEmail.error =validateUserEmail.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAdminRegisterEmail.error =validateUserEmail.errorMessage
                }

            }
        }
        when(validationAge){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAdminRegisterAge.error =validationAge.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAdminRegisterAge.error =validationAge.errorMessage
                }

            }
        }

        when(fnameValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAdminRegisterFName.error =fnameValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAdminRegisterFName.error =fnameValidation.errorMessage
                }

            }
        }
        when(lnameValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAdminRegisterLName.error =lnameValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAdminRegisterLName.error =lnameValidation.errorMessage
                }

            }
        }

        when(passwordValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAdminRegisterPass.error =passwordValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAdminRegisterPass.error =passwordValidation.errorMessage
                }

            }
        }

        when(telValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAdminRegisterTel.error =telValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAdminRegisterTel.error =telValidation.errorMessage
                }

            }
        }
        if(count==6){
            //initializing db Credentils and storing data
            val currentDateTime = Timestamp(System.currentTimeMillis())

            // Get an instance of the TouristoDB database
            val db = TouristoDB.getInstance(application)

            // Get the UserDao from the database
            val adminDao= db.adminDao()
            val adminRepo = AdminRepository(adminDao, Dispatchers.IO)

            if(adminRepo.userExist(adminEmail)>0){
                lifecycleScope.launch(Dispatchers.Main) {
                    //initializing the dialogBox Class
                    confirmationDialog = ConfirmationDialog(this@RegisterAdmin)
                    //lamdas starts here
                    confirmationDialog.dialogWithInfo("Account Already Exists") {
                        //do anything
                    }
                }
            }else{
                lifecycleScope.launch(Dispatchers.Main) {
                    adminRepo.insertAdmin(Admin(0,adminFname,adminLname,adminEmail,adminPass,adminImg,adminTEl,adminAge.toInt(),adminGender,adminDesignation,currentDateTime.toString(),""))
                    //initializing the dialogBox Class
                    confirmationDialog = ConfirmationDialog(this@RegisterAdmin)
                    confirmationDialog.dialogWithSuccess("You have successfully registered") {
                        val intent = Intent(this@RegisterAdmin, AdminHome::class.java).apply {
                            putExtra("replaceFragment", "AdminManagement")
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