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
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.touristo.dao.UserDao
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.modal.User
import com.example.touristo.repository.UserRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.log

class EditProfile : AppCompatActivity() {
    private lateinit var btnEditProfileUpdate:Button
    private lateinit var etEditProfileCountry:EditText
    private lateinit var etEditProfileAge:EditText
    private lateinit var etEditProfileGender:EditText
    private lateinit var etEditProfileTel:EditText
    private lateinit var etEditProfilePassword:EditText
    private lateinit var etEditProfileEmail:EditText
    private lateinit var imgShapeEditProfile:ImageView
    private lateinit var tvEditProfileUName:TextView
    private lateinit var tvEditProfileEmail:TextView
    private lateinit var fbEditProfileBtnPencil:FloatingActionButton
    private lateinit var fbEditProfileBtn:FloatingActionButton
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

            etEditProfileGender.setText(user.gender)
            etEditProfileTel.setText(user.tel)
            etEditProfilePassword.setText(user.password)

            etEditProfileEmail.setText(user.uname)

            tvEditProfileEmail.text = user.uemail
            tvEditProfileUName.text = user.uname

            btnEditProfileUpdate.setOnClickListener {

                GlobalScope.launch(Dispatchers.IO) {
                    updateUserProfile(user.uemail)
                }
            }
        }
    }
    suspend fun updateUserProfile( email: String){
        val country = etEditProfileCountry.text.toString()
        val password = etEditProfilePassword.text.toString()
        val tel = etEditProfileTel.text.toString()
        val gender = etEditProfileGender.text.toString()
        val age = etEditProfileAge.text.toString()
        val uName = etEditProfileEmail.text.toString()

        println("Country: $country")
        println("Email: $email")
        println("Password: $password")
        println("Tel: $tel")
        println("Gender: $gender")
        println("Age: $age")
        println("UName: $uName")
        val db = TouristoDB.getInstance(application)
        // Get the UserDao from the database
        val userDao = db.userDao()
        val userRepo = UserRepository(userDao, Dispatchers.IO)

        val result : Int = userRepo.updateUserProfile(country,gender,age.toInt(),tel,"propic",password,uName,email)

        if(result>0){
            GlobalScope.launch(Dispatchers.Main){
                showCustomDialogWithAutoLayoutHeight(this@EditProfile,"success","You have successfully registered")
            }

        }else{
            GlobalScope.launch(Dispatchers.Main){
                showCustomDialogWithAutoLayoutHeight(this@EditProfile,"error","Invalid Credentials")
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
        val dgOkBtn = dialog.findViewById<Button>(R.id.btnDgOk)
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
        }else if(title.equals("success", ignoreCase = true)){
            color = ContextCompat.getColor(this, R.color.bgDialogSuccess)
            dgOkBtn.backgroundTintList = ColorStateList.valueOf(color)

            dgOkBtn.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }
    override fun onDestroy() {
        super.onDestroy()
        dialog = Dialog(this@EditProfile)
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }



}