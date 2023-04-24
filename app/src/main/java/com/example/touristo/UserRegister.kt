package com.example.touristo

import android.annotation.SuppressLint
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
import android.view.MotionEvent
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.touristo.dbCon.TouristoDB
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
    private lateinit var dialog: Dialog
    private var count = 0;

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
            GlobalScope.launch(Dispatchers.IO) {
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
    //function for the custom Alert

    private fun showCustomDialogWithAutoLayoutHeight(context: Context, title :String, description:String) {
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
        if(title.equals("infocoloredred", ignoreCase = true)){
            color = ContextCompat.getColor(this, R.color.bgDialogError)
            dgOkBtn.backgroundTintList = ColorStateList.valueOf(color)

            dgOkBtn.setOnClickListener {
                dialog.dismiss()
            }
        }else if(title.equals("success", ignoreCase = true)){
            color = ContextCompat.getColor(this, R.color.bgDialogSuccess)
            dgOkBtn.backgroundTintList = ColorStateList.valueOf(color)

            dgOkBtn.setOnClickListener {
                startActivity(Intent(this@UserRegister,UserLogin::class.java))
                finish()
            }
        }


        dialog.show()
    }
    //function for the userForm Submission
    suspend fun userRegSubmit(){
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
                GlobalScope.launch(Dispatchers.Main) {
                    etURegEmail.error =emailValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etURegEmail.error =emailValidation.errorMessage
                }

            }
        }

        when(nameValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etURegName.error =nameValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etURegName.error =nameValidation.errorMessage
                }

            }
        }

        when(passwordValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etURegPass.error =passwordValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etURegPass.error =passwordValidation.errorMessage
                }

            }
        }

        when(telValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                GlobalScope.launch(Dispatchers.Main) {
                    etURegTel.error =telValidation.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                GlobalScope.launch(Dispatchers.Main) {
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
                GlobalScope.launch(Dispatchers.Main) {

                    showCustomDialogWithAutoLayoutHeight(this@UserRegister,"infocoloredred","Account Already Exists")
                }
            }else{
                GlobalScope.launch(Dispatchers.Main) {
                    userRepo.insertUser(User(0,dbName,dbEmail,dbPassword,null,dbTel,null,null,null,currentDateTime.toString(),))
                    showCustomDialogWithAutoLayoutHeight(this@UserRegister,"success","You have successfully registered")
                }
            }
            count=0;
        }
        count=0;
    }
    override fun onDestroy() {
        super.onDestroy()
        GlobalScope.launch(Dispatchers.Main) {
            dialog = Dialog(this@UserRegister)
            dialog.dismiss() // Dismiss the dialog if it's still showing
        }

    }
}