package com.example.touristo

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.formData.UserRegisterForm
import com.example.touristo.modal.User
import com.example.touristo.validations.ValidationResult
import com.example.touristo.viewModal.UserViewModal
import com.example.touristo.viewModalProvider.UserViewModalFactory
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
    private lateinit var userViewModal: UserViewModal
    private lateinit var dialog: Dialog
    private var count = 0;
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
            userRegSubmit()
        }
        tvURegSignIn.setOnClickListener {
            startActivity(Intent(this@UserRegister,UserLogin::class.java))
            finish()
        }

    }

    //function for the custom Alert
    fun showCustomDialogWithAutoLayoutHeight(context: Context , title :String , description:String) {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_success)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val dgTitle = dialog.findViewById<TextView>(R.id.tvDgTitle)
        val dgDescription = dialog.findViewById<TextView>(R.id.tvDgDecsrition)
        val dgOkBtn = dialog.findViewById<TextView>(R.id.btnDgOk)

        dgTitle.text = title
        dgDescription.text = description

        dgOkBtn.setOnClickListener {
            startActivity(Intent(this@UserRegister,UserLogin::class.java))
            finish()
        }

        dialog.show()
    }
    //function for the userForm Submission
    fun userRegSubmit(){
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
                etURegEmail.error =emailValidation.errorMessage
            }
            is ValidationResult.Empty ->{
                etURegEmail.error =emailValidation.errorMessage
            }
        }

        when(nameValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                etURegName.error =nameValidation.errorMessage
            }
            is ValidationResult.Empty ->{
                etURegName.error =nameValidation.errorMessage
            }
        }

        when(passwordValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                etURegPass.error =passwordValidation.errorMessage
            }
            is ValidationResult.Empty ->{
                etURegPass.error =passwordValidation.errorMessage
            }
        }

        when(telValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                etURegTel.error =telValidation.errorMessage
            }
            is ValidationResult.Empty ->{
                etURegTel.error =telValidation.errorMessage
            }
        }
        if(count==4) {

            //initializing db Credentils and storing data
            val currentDateTime = Timestamp(System.currentTimeMillis())

            // Get an instance of the TouristoDB database
            val db = TouristoDB.getInstance(application)

            // Get the UserDao from the database
            val userDao = db.userDao()
            val userFactory = UserViewModalFactory(userDao)
            userViewModal = ViewModelProvider(this@UserRegister,userFactory)[UserViewModal::class.java]
//            val exists = userViewModal.getUserExist(dbEmail)
//            print(exists)
            userViewModal.insertUser(User(0,dbName,dbEmail,dbPassword,null,dbTel,null,null,null,currentDateTime.toString(),))

            showCustomDialogWithAutoLayoutHeight(this@UserRegister,"Success","You have successfully registered")
            count=0;
        }
        count=0;
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog.dismiss() // Dismiss the dialog if it's still showing
    }
}