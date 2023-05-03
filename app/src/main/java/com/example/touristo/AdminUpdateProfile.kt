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
import com.example.touristo.formData.AdminProfileValidation
import com.example.touristo.formData.UserProfileValidation
import com.example.touristo.modal.Admin
import com.example.touristo.modal.User
import com.example.touristo.repository.AdminRepository
import com.example.touristo.repository.UserRepository
import com.example.touristo.validations.ValidationResult
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class AdminUpdateProfile : AppCompatActivity() {
    private lateinit var aemail:String
    private lateinit var db:TouristoDB
    private lateinit var confirmationDialog:ConfirmationDialog
    val currentDateTime = Timestamp(System.currentTimeMillis())
    var count:Int =0

    private lateinit var btnAdminProfileCancel:Button
    private lateinit var btnAdminEditProfile:Button
    private lateinit var btnAdminProfileSubmit:Button
    private lateinit var etAdminProfileEditAge:EditText
    private lateinit var etAdminEditProfileDesignation:Spinner
    private lateinit var spAdmniEditProfileGender:Spinner
    private lateinit var etAdminEditProfileNumber:EditText
    private lateinit var etAdminEditProfileLname:EditText
    private lateinit var etAdminEditProfileFname:EditText
    private lateinit var etAdminProfilePassword:EditText
    private lateinit var etAdminEditProfileEail:EditText
    private lateinit var fbAdminEditProfilePencil:FloatingActionButton
    private lateinit var simgAdminProfileUpdatePic:ImageView
    private lateinit var openerImg:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_update_profile)

        //the if block is executed so that the notification pannel color changes and the Icon of them changes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.bgBackground)

            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        //iniitialize the views
        btnAdminProfileCancel = findViewById(R.id.btnAdminProfileCancel)
        btnAdminEditProfile = findViewById(R.id.btnAdminEditProfile)
        btnAdminProfileSubmit = findViewById(R.id.btnAdminProfileSubmit)
        etAdminProfileEditAge = findViewById(R.id.etAdminProfileEditAge)
        etAdminEditProfileDesignation = findViewById(R.id.etAdminEditProfileDesignation)
        spAdmniEditProfileGender = findViewById(R.id.spAdmniEditProfileGender)
        etAdminEditProfileNumber = findViewById(R.id.etAdminEditProfileNumber)
        etAdminEditProfileLname = findViewById(R.id.etAdminEditProfileLname)
        etAdminEditProfileFname = findViewById(R.id.etAdminEditProfileFname)
        etAdminProfilePassword = findViewById(R.id.etAdminProfilePassword)
        etAdminEditProfileEail = findViewById(R.id.etAdminEditProfileEail)
        fbAdminEditProfilePencil = findViewById(R.id.fbAdminEditProfilePencil)
        simgAdminProfileUpdatePic = findViewById(R.id.simgAdminProfileUpdatePic)
        openerImg = findViewById(R.id.openerImg)

        etAdminEditProfileEail.isEnabled = false
        openerImg.setOnClickListener {
            finish()
        }
        btnAdminProfileCancel.setOnClickListener{
            finish()
        }

        val bundle = intent.extras
        val admin = bundle?.getSerializable("admin") as? Admin
        aemail = bundle?.getString("amail").toString()
        if(admin!=null){
            etAdminProfileEditAge.setText(admin.age.toString())

            var position:Int =0
            if(admin.gender.toString() == "Male"){
                position =0
            }else if(admin.gender.toString()=="Female"){
                position=1
            }

            var positionOfDesignation:Int =0
            if(admin.gender == "Admin"){
                positionOfDesignation =0
            }else if(admin.gender=="Staff"){
                positionOfDesignation=1
            }else if(admin.gender=="Employee"){
                positionOfDesignation=2
            }
            etAdminEditProfileDesignation.setSelection(positionOfDesignation)
            spAdmniEditProfileGender.setSelection(position)

            etAdminEditProfileNumber.setText(admin.tel)
            etAdminEditProfileLname.setText(admin.lname)
            etAdminEditProfileFname.setText(admin.fname)
            etAdminProfilePassword.setText(admin.password)
            etAdminEditProfileEail.setText(admin.aemail)
            etAdminEditProfileEail.setText(admin.aemail)
            btnAdminEditProfile.setText(dateFormatter(admin.added))

            lifecycleScope.launch(Dispatchers.IO){
                setEditProfileForm(admin)

            }
        }
    }

    private fun setEditProfileForm(admin: Admin) {
        btnAdminProfileSubmit.setOnClickListener {
            val dbLName = etAdminEditProfileLname.text.toString()
            val dbFName = etAdminEditProfileFname.text.toString()
            val dbEmail = etAdminEditProfileEail.text.toString()
            val dbPassword =  etAdminProfilePassword.text.toString()
            val dbTel = etAdminEditProfileNumber.text.toString()
            val dbPropic = ""

            val dbAge:Int = if(etAdminProfileEditAge.text.toString().isEmpty() || etAdminProfileEditAge.text.toString()==""){
                0
            }else{
                etAdminProfileEditAge.text.toString().toInt()
            }
            val dbGender = spAdmniEditProfileGender.selectedItem.toString()
            val dbDesignation = etAdminEditProfileDesignation.selectedItem.toString()


            val userEditForm = AdminProfileValidation(
                dbFName,
                dbLName,
                dbPassword,
                dbTel,
                dbAge.toString()
            )
            val validationAge =userEditForm.validationAge()
            val validateFirstName =userEditForm.validateFirstName()
            val validateLastName =userEditForm.validateLastName()
            val passwordValidation =userEditForm.validatePassword()
            val telValidation =userEditForm.validateTel()

            when(validationAge){
                is ValidationResult.Valid ->{ count ++ }
                is ValidationResult.Invalid ->{
                    lifecycleScope.launch(Dispatchers.Main) {
                        etAdminProfileEditAge.error =validationAge.errorMessage
                    }

                }
                is ValidationResult.Empty ->{
                    lifecycleScope.launch(Dispatchers.Main) {
                        etAdminProfileEditAge.error =validationAge.errorMessage
                    }

                }
            }

            when(validateFirstName){
                is ValidationResult.Valid ->{ count ++ }
                is ValidationResult.Invalid ->{
                    lifecycleScope.launch(Dispatchers.Main) {
                        etAdminEditProfileFname.error =validateFirstName.errorMessage
                    }

                }
                is ValidationResult.Empty ->{
                    lifecycleScope.launch(Dispatchers.Main) {
                        etAdminEditProfileFname.error =validateFirstName.errorMessage
                    }

                }
            }

            when(validateLastName){
                is ValidationResult.Valid ->{ count ++ }
                is ValidationResult.Invalid ->{
                    lifecycleScope.launch(Dispatchers.Main) {
                        etAdminEditProfileLname.error =validateLastName.errorMessage
                    }

                }
                is ValidationResult.Empty ->{
                    lifecycleScope.launch(Dispatchers.Main) {
                        etAdminEditProfileLname.error =validateLastName.errorMessage
                    }

                }
            }

            when(passwordValidation){
                is ValidationResult.Valid ->{ count ++ }
                is ValidationResult.Invalid ->{
                    lifecycleScope.launch(Dispatchers.Main) {
                        etAdminProfilePassword.error =passwordValidation.errorMessage
                    }

                }
                is ValidationResult.Empty ->{
                    lifecycleScope.launch(Dispatchers.Main) {
                        etAdminProfilePassword.error =passwordValidation.errorMessage
                    }

                }
            }

            when(telValidation){
                is ValidationResult.Valid ->{ count ++ }
                is ValidationResult.Invalid ->{
                    lifecycleScope.launch(Dispatchers.Main) {
                        etAdminEditProfileNumber.error =telValidation.errorMessage
                    }

                }
                is ValidationResult.Empty ->{
                    lifecycleScope.launch(Dispatchers.Main) {
                        etAdminEditProfileNumber.error =telValidation.errorMessage
                    }

                }
            }

            if(count==5){
                lifecycleScope.launch(Dispatchers.IO){
                    db = TouristoDB.getInstance(application)
                    // Get the adminDao from the database
                    val adminDao = db.adminDao()
                    val adminRepo = AdminRepository(adminDao, Dispatchers.IO)
                    val result : Int = adminRepo.updateAdminProfile(dbFName,dbLName,dbPassword,dbAge,dbTel,dbPropic,dbGender,dbDesignation,currentDateTime.toString(),dbEmail)
                    lifecycleScope.launch(Dispatchers.Main){
                        confirmationDialog = ConfirmationDialog(this@AdminUpdateProfile)
                        if(result>0){
                            confirmationDialog.dialogWithSuccess("Profile Updated") {
                                val intent = Intent(this@AdminUpdateProfile, AdminHome::class.java).apply {
                                    putExtra("replaceFragment", "AdminManagement")
                                    putExtra("adminEmail", aemail)
                                }
                                startActivity(intent)
                                finish()

                            }
                        }else{
                            confirmationDialog.dialogWithError("Invalid Credentials") {
                                //do anything
                            }
                        }
                    }
                }
                count=0
            }
            count=0

        }
    }

    private fun dateFormatter(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())

// Convert input string to date
        val date = inputFormat.parse(date)
        // Extract day of month from parsed date
        val dayOfMonth = date.day
        val outputFormat = SimpleDateFormat("dd'${getDaySuffix(dayOfMonth)}' MMM yyyy", Locale.getDefault())

// Format date to output string
        val outputDateString = outputFormat.format(date)
        return outputDateString.toString()

    }
    // Function to get the day suffix
    private fun getDaySuffix(day: Int): String {
        if (day in 11..13) {
            return "th"
        }
        return when (day % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }
}