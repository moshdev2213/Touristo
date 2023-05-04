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
import com.example.touristo.formData.AdminRegisterValidation
import com.example.touristo.formData.VillaRegisterValidation
import com.example.touristo.modal.Admin
import com.example.touristo.modal.Villa
import com.example.touristo.repository.AdminRepository
import com.example.touristo.repository.VillaRepository
import com.example.touristo.validations.ValidationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Timestamp

class VillaProfileHandling : AppCompatActivity() {
    private var count=0
    private lateinit var db:TouristoDB
    private lateinit var villa:Villa
    private lateinit var aemail:String
    private lateinit var confirmationDialog:ConfirmationDialog

    private lateinit var btnAddVilaCancel:Button
    private lateinit var btnAddVilaSubmit:Button

    private lateinit var imgAddVilaPic3:ImageView
    private lateinit var imgAddVilapic2:ImageView
    private lateinit var imdAddVilaPic1:ImageView
    private lateinit var imgAddVilaPic4:ImageView

    private lateinit var spAddVilaProvince:Spinner
    private lateinit var spAddVilaType:Spinner
    private lateinit var spAddVilaDistrict:Spinner

    private lateinit var etAddVilaPrice:EditText
    private lateinit var etAddVilaName:EditText
    private lateinit var etAddVilaDescription:EditText
    private lateinit var etVillaRating:EditText
    private lateinit var openerImg:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_villa_profile_handling)

        //the if block is executed so that the notification pannel color changes and the Icon of them changes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.bgBackground)

            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        //coe starts here
        openerImg = findViewById(R.id.openerImg)
        btnAddVilaCancel = findViewById(R.id.btnAddVilaCancel)
        btnAddVilaSubmit= findViewById(R.id.btnAddVilaSubmit)


        val intent = intent.extras
        aemail = intent?.getString("amail").toString()

        btnAddVilaCancel.setOnClickListener {
            finish()
        }
        openerImg.setOnClickListener {
            finish()
        }
        btnAddVilaSubmit.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){
                addNewVilla()
            }
        }
    }

    private suspend fun addNewVilla() {
        imgAddVilaPic3= findViewById(R.id.imgAddVilaPic3)
        imgAddVilapic2= findViewById(R.id.imgAddVilapic2)
        imdAddVilaPic1= findViewById(R.id.imdAddVilaPic1)
        imgAddVilaPic4= findViewById(R.id.imgAddVilaPic4)
        spAddVilaProvince= findViewById(R.id.spAddVilaProvince)
        spAddVilaType= findViewById(R.id.spAddVilaType)
        spAddVilaDistrict= findViewById(R.id.spAddVilaDistrict)
        etAddVilaPrice= findViewById(R.id.etAddVilaPrice)
        etAddVilaName= findViewById(R.id.etAddVilaName)
        etAddVilaDescription= findViewById(R.id.etAddVilaDescription)
        etVillaRating= findViewById(R.id.etVillaRating)

        val villaImg1  = "propic1"
        val villaImg2  = "propic1"
        val villaImg3  = "propic1"
        val villaImg4  = "propic1"

        val villaDecription  = etAddVilaDescription.text.toString()
        val villaName  = etAddVilaName.text.toString()
        val villaPrice  = etAddVilaPrice.text.toString()
        val villaDistrict  = spAddVilaDistrict.selectedItem.toString()
        val villaProvince  = spAddVilaProvince.selectedItem.toString()
        val villaType  = spAddVilaType.selectedItem.toString()
        val villaRating  = etVillaRating.text.toString()

        val villaEditForm = VillaRegisterValidation(
            villaName,
            villaDecription,
            villaPrice,
            villaRating
        )
        val validateDescription =villaEditForm.validateDescription()
        val validateVillaName =villaEditForm.validateVillaName()
        val validatePrice =villaEditForm.validatePrice()
        val validateRating =villaEditForm.validateRating()

        when(validateRating){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etVillaRating.error =validateRating.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etVillaRating.error =validateRating.errorMessage
                }

            }
        }
        when(validateDescription){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAddVilaDescription.error =validateDescription.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAddVilaDescription.error =validateDescription.errorMessage
                }

            }
        }
        when(validateVillaName){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAddVilaName.error =validateVillaName.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAddVilaName.error =validateVillaName.errorMessage
                }

            }
        }

        when(validatePrice){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAddVilaPrice.error =validatePrice.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etAddVilaPrice.error =validatePrice.errorMessage
                }

            }
        }

        if(count==4){
            //initializing db Credentils and storing data
            val currentDateTime = Timestamp(System.currentTimeMillis())

            // Get an instance of the TouristoDB database
            val db = TouristoDB.getInstance(application)

            // Get the UserDao from the database
            val villaDao= db.villaDao()
            val villaRepo = VillaRepository(villaDao, Dispatchers.IO)

            lifecycleScope.launch(Dispatchers.Main) {
                villaRepo.insertVilla(Villa(
                    0,
                    villaName,
                    villaPrice.toDouble(),
                    villaType,
                    villaRating.toDouble(),
                    villaDecription,
                    villaDistrict,
                    villaProvince,
                    villaImg1,
                    villaImg2,
                    villaImg3,
                    villaImg4,
                    currentDateTime.toString(),
                    ""))
                //initializing the dialogBox Class
                confirmationDialog = ConfirmationDialog(this@VillaProfileHandling)
                confirmationDialog.dialogWithSuccess("You have successfully registered") {
                    val intent = Intent(this@VillaProfileHandling, AdminHome::class.java).apply {
                        putExtra("replaceFragment", "VillaManagement")
                        putExtra("adminEmail", aemail)
                    }
                    startActivity(intent)
                    finish()
                }
            }
            count=0
        }
        count=0

    }
}