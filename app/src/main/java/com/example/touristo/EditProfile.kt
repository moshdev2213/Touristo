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
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.touristo.dao.UserDao
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.ConfirmationDialog
import com.example.touristo.formData.UserProfileValidation
import com.example.touristo.modal.User
import com.example.touristo.repository.UserRepository
import com.example.touristo.validations.ValidationResult
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.log

class EditProfile : AppCompatActivity() {
    private lateinit var btnEditProfileUpdate:Button
    private lateinit var etEditProfileCountry:EditText
    private lateinit var etEditProfileAge:EditText
    private lateinit var etEditProfileGender:Spinner
    private lateinit var etEditProfileTel:EditText
    private lateinit var etEditProfilePassword:EditText
    private lateinit var etEditProfileEmail:EditText
    private lateinit var imgShapeEditProfile:ImageView
    private lateinit var tvEditProfileUName:TextView
    private lateinit var tvEditProfileEmail:TextView
    private lateinit var fbEditProfileBtnPencil:FloatingActionButton
    private lateinit var fbEditProfileBtn:FloatingActionButton
    private var count = 0;


    private lateinit var confirmationDialog : ConfirmationDialog
    // Get a reference to the EditText field and the toggle button

    private lateinit var toggleButton: ImageView
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
// In Activity's onCreate() for instance this transparents the background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        //initialoiozing the views
        etEditProfileCountry=findViewById(R.id.etEditProfileCountry)
        etEditProfileEmail=findViewById(R.id.etEditProfileEmail)
        etEditProfilePassword=findViewById(R.id.etEditProfilePassword)
        etEditProfileTel=findViewById(R.id.etEditProfileTel)
        etEditProfileGender=findViewById(R.id.etEditProfileGender)
        etEditProfileAge=findViewById(R.id.etEditProfileAge)

        tvEditProfileEmail=findViewById(R.id.tvEditProfileEmail)
        tvEditProfileUName=findViewById(R.id.tvEditProfileUName)

        toggleButton = findViewById(R.id.imgEditProfileTogglePwd)
        fbEditProfileBtn = findViewById(R.id.fbEditProfileBtn)
        fbEditProfileBtn.setOnClickListener {
            finish()
        }
        toggleButton.setOnClickListener {
            if (etEditProfilePassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                // Show the password
                etEditProfilePassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                toggleButton.setImageResource(R.drawable.baseline_key_off_24)
            } else {
                // Hide the password
                etEditProfilePassword.transformationMethod = PasswordTransformationMethod.getInstance()
                toggleButton.setImageResource(R.drawable.baseline_key_24)
            }
        }

        btnEditProfileUpdate = findViewById(R.id.btnEditProfileUpdate)
        val bundle = intent.extras
        val user = bundle?.getSerializable("user") as? User
        if (user != null) {

            etEditProfileCountry.setText(user.country)
            user.age?.let { etEditProfileAge.setText(it.toString()) }

            var position:Int =0
            if(user.gender.toString() == "Male"){
                position =0
            }else if(user.gender.toString()=="Female"){
                position=1
            }
            etEditProfileGender.setSelection(position)
            etEditProfileTel.setText(user.tel)
            etEditProfilePassword.setText(user.password)

            etEditProfileEmail.setText(user.uname)

            tvEditProfileEmail.text = user.uemail
            tvEditProfileUName.text = user.uname

            btnEditProfileUpdate.setOnClickListener {

                lifecycleScope.launch(Dispatchers.IO) {
                    updateUserProfile(user.uemail)
                }
            }
        }
    }
    private suspend fun updateUserProfile(email: String){
        val country = etEditProfileCountry.text.toString()
        val password = etEditProfilePassword.text.toString()
        val tel = etEditProfileTel.text.toString()
        val gender = etEditProfileGender.selectedItem.toString()
        val age = etEditProfileAge.text.toString()
        val uName = etEditProfileEmail.text.toString()

        val userEditForm = UserProfileValidation(
            uName,
            password,
            tel,
            "",
            age,
            country
        )

        val validationAge =userEditForm.validationAge()
        val nameValidation =userEditForm.validateUserName()
        val passwordValidation =userEditForm.validatePassword()
        val telValidation =userEditForm.validateTel()
        val validateCountry =userEditForm.validateCountry()

        when(validationAge){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditProfileAge.error =validationAge.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditProfileAge.error =validationAge.errorMessage
                }

            }
        }

        when(nameValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditProfileEmail.error =nameValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditProfileEmail.error =nameValidation.errorMessage
                }

            }
        }

        when(passwordValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditProfilePassword.error =passwordValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditProfilePassword.error =passwordValidation.errorMessage
                }

            }
        }

        when(telValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditProfileTel.error =telValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditProfileTel.error =telValidation.errorMessage
                }

            }
        }
        when(validateCountry){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditProfileCountry.error =validateCountry.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditProfileCountry.error =validateCountry.errorMessage
                }

            }
        }
        if(count==5){

            val db = TouristoDB.getInstance(application)
            // Get the UserDao from the database
            val userDao = db.userDao()
            val userRepo = UserRepository(userDao, Dispatchers.IO)

            val result : Int = userRepo.updateUserProfile(country,gender,age.toInt(),tel,"propic",password,uName,email)
            lifecycleScope.launch(Dispatchers.Main){
                confirmationDialog = ConfirmationDialog(this@EditProfile)
                if(result>0){
                    confirmationDialog.dialogWithSuccess("You have successfully registered") {
                        finish()
                    }
                }else{
                    confirmationDialog.dialogWithError("Invalid Credentials") {
                        //do anything
                    }
                }
            }
            count=0
        }
        count=0
    }
}