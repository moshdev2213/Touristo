package com.example.touristo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.ConfirmationDialog
import com.example.touristo.formData.VillaRegisterValidation
import com.example.touristo.modal.Admin
import com.example.touristo.modal.Villa
import com.example.touristo.repository.VillaRepository
import com.example.touristo.validations.ValidationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Timestamp

class VillaEditProfile : AppCompatActivity() {
    private lateinit var aemail:String
    private lateinit var db: TouristoDB
    private lateinit var confirmationDialog: ConfirmationDialog
    val currentDateTime = Timestamp(System.currentTimeMillis())
    var count:Int =0

    private lateinit var btnEditVillaCancel: Button
    private lateinit var btnEditVillaSubmit: Button
    private lateinit var openerImg: ImageView

    private lateinit var simgEditVillaBPic: ImageView
    private lateinit var tcEditVillaBDistrict: TextView
    private lateinit var tvEditVillaBName: TextView
    private lateinit var btnEditVillaBPrice: Button

    private lateinit var imdEditVillaPic1: ImageView

    private lateinit var spEditVillaProvince: Spinner
    private lateinit var spEditVillaDistrict: Spinner
    private lateinit var spEditVillaType: Spinner

    private lateinit var etEditVillaPrice: EditText
    private lateinit var etEditVillaName: EditText
    private lateinit var etEditVillaDescription: EditText
    private lateinit var etVillaEditRating: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_villa_edit_profile)

        //the if block is executed so that the notification pannel color changes and the Icon of them changes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.bgBackground)

            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        spEditVillaProvince= findViewById(R.id.spEditVillaProvince)
        spEditVillaType= findViewById(R.id.spEditVillaType)
        spEditVillaDistrict= findViewById(R.id.spEditVillaDistrict)
        etEditVillaPrice= findViewById(R.id.etEditVillaPrice)
        etEditVillaName= findViewById(R.id.etEditVillaName)
        etEditVillaDescription= findViewById(R.id.etEditVillaDescription)
        etVillaEditRating= findViewById(R.id.etVillaEditRating)

        btnEditVillaBPrice= findViewById(R.id.btnEditVillaBPrice)
        tvEditVillaBName= findViewById(R.id.tvEditVillaBName)
        tcEditVillaBDistrict= findViewById(R.id.tcEditVillaBDistrict)
        simgEditVillaBPic= findViewById(R.id.simgEditVillaBPic)

        openerImg= findViewById(R.id.openerImg)
        btnEditVillaSubmit= findViewById(R.id.btnEditVillaSubmit)
        btnEditVillaCancel= findViewById(R.id.btnEditVillaCancel)

        openerImg.setOnClickListener {
            finish()
        }
        btnEditVillaCancel.setOnClickListener{
            finish()
        }
        val bundle = intent.extras
        val villa = bundle?.getSerializable("villa") as? Villa
        aemail = bundle?.getString("amail").toString()

        if(villa!=null){
            var positionOfDistrict:Int =0
            var positionOfProvince:Int =0
            var positionOfRoomType:Int =0
            positionOfDistrict = districtIndexChecker(villa.district.toString())
            positionOfProvince = provinceIndexChecker(villa.province.toString())
            positionOfRoomType = roomTypeIndexChecker(villa.roomType.toString())

            spEditVillaType.setSelection(positionOfRoomType)
            spEditVillaDistrict.setSelection(positionOfDistrict)
            spEditVillaProvince.setSelection(positionOfProvince)

            imdEditVillaPic1.setImageResource(R.drawable.interiordemo) // Set image resource


            etVillaEditRating.setText(villa.rating.toString())
            etEditVillaDescription.setText(villa.description)
            etEditVillaName.setText(villa.villaName)
            etEditVillaPrice.setText(villa.price.toString())

            simgEditVillaBPic.setImageResource(R.drawable.interiordemo)
            tcEditVillaBDistrict.text = villa.district
            tvEditVillaBName.text = villa.villaName
            btnEditVillaBPrice.text = "Rs ${String.format("%.2f", villa.price)}"

            btnEditVillaSubmit.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO){
                    setEditProfileForm(villa)

                }
            }

        }
    }

    private fun setEditProfileForm(villa: Villa) {
        val villaImg1  = "interiordemo"
        val villaImg2  = "interiordemo"
        val villaImg3  = "interiordemo"
        val villaImg4  = "interiordemo"

        val villaDecription  = etEditVillaDescription.text.toString()
        val villaName  = etEditVillaName.text.toString()
        val villaPrice  = etEditVillaPrice.text.toString()
        val villaDistrict  = spEditVillaDistrict.selectedItem.toString()
        val villaProvince  = spEditVillaProvince.selectedItem.toString()
        val villaType  = spEditVillaType.selectedItem.toString()
        var villaRating  = etVillaEditRating.text.toString()

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
                    etVillaEditRating.error =validateRating.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etVillaEditRating.error =validateRating.errorMessage
                }

            }
        }
        when(validateDescription){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditVillaDescription.error =validateDescription.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditVillaDescription.error =validateDescription.errorMessage
                }

            }
        }
        when(validateVillaName){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditVillaName.error =validateVillaName.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditVillaName.error =validateVillaName.errorMessage
                }

            }
        }

        when(validatePrice){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditVillaPrice.error =validatePrice.errorMessage
                }

            }
            is ValidationResult.Empty ->{
                lifecycleScope.launch(Dispatchers.Main) {
                    etEditVillaPrice.error =validatePrice.errorMessage
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
                villaRepo.updateVillaProfile(villaName, villaPrice, villaType,villaRating,villaDecription, villaDistrict, villaProvince, villaImg1, villaImg2, villaImg3, villaImg4, currentDateTime.toString(),villa.id)

                //initializing the dialogBox Class
                confirmationDialog = ConfirmationDialog(this@VillaEditProfile)
                confirmationDialog.dialogWithSuccess("You have successfully registered") {
                    val intent = Intent(this@VillaEditProfile, AdminHome::class.java).apply {
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

    private fun districtIndexChecker(district:String):Int{
        val districts = arrayOf(
            "Ampara", "Anuradhapura", "Badulla", "Batticaloa", "Colombo",
            "Galle", "Gampaha", "Hambantota", "Jaffna", "Kalutara", "Kandy",
            "Kegalle", "Kilinochchi", "Kurunegala", "Mannar", "Matale",
            "Matara", "Monaragala", "Mullaitivu", "Nuwara Eliya", "Polonnaruwa",
            "Puttalam", "Ratnapura", "Trincomalee", "Vavuniya"
        )
        return districts.indexOf(district)
    }

    private fun provinceIndexChecker(province:String):Int{
        val provinces = arrayOf(
            "Western Province", "Central Province", "Southern Province",
            "Northern Province", "Eastern Province", "North Western Province",
            "North Central Province", "Uva Province", "Sabaragamuwa Province"
        )

        return provinces.indexOf(province)
    }
    private fun roomTypeIndexChecker(room:String):Int{
        val rooms = arrayOf(
           "Standard","Deluxe","UltraDeluxe"
        )
        return rooms.indexOf(room)
    }

}