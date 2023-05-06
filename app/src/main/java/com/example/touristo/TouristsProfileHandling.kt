package com.example.touristo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.touristo.Fragments.AdminHomeFrag
import com.example.touristo.Fragments.AdminManagement
import com.example.touristo.Fragments.TouristManagement
import com.example.touristo.Fragments.VillaManagement
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.ProgressLoader
import com.example.touristo.modal.User
import com.example.touristo.repository.UserRepository
import com.google.android.material.navigation.NavigationView
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class TouristsProfileHandling : AppCompatActivity() {
    private lateinit var user:User
    private lateinit var aemail:String
    private lateinit var db:TouristoDB
    private lateinit var byteArray:ByteArray
    private lateinit var progressLoader: ProgressLoader
    private lateinit var touristDetail: Array<String>
    val currentDateTime = Timestamp(System.currentTimeMillis())

    private lateinit var openerImg:ImageView
    private lateinit var simTPHUserImg:ImageView
    private lateinit var btnTPHUserJoined:Button
    private lateinit var tvTPHUserTel:TextView
    private lateinit var tvTPHUserCountry:TextView
    private lateinit var tvTPHUserName:TextView
    private lateinit var tvTPHPayDate:TextView
    private lateinit var btnTPHUserPayAmt:Button
    private lateinit var tvTPHUserLogCount:TextView
    private lateinit var tvTPHUserInquiryCount:TextView
    private lateinit var tvTPHUserLastBookDate:TextView
    private lateinit var tvTPHUserLastBookedLocation:TextView
    private lateinit var tvTMPUserPaymnetMode:TextView
    private lateinit var btnTPHUserBookedTotal:Button
    private lateinit var btnTPHUserEditAccount:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tourists_profile_handling)

        //the if block is executed so that the notification pannel color changes and the Icon of them changes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.bgBackground)

            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        //coding starts here
        simTPHUserImg = findViewById(R.id.simTPHUserImg)
        btnTPHUserJoined= findViewById(R.id.btnTPHUserJoined)
        tvTPHUserTel= findViewById(R.id.tvTPHUserTel)
        tvTPHUserCountry= findViewById(R.id.tvTPHUserCountry)
        tvTPHUserName= findViewById(R.id.tvTPHUserName)
        tvTPHPayDate= findViewById(R.id.tvTPHPayDate)
        btnTPHUserPayAmt= findViewById(R.id.btnTPHUserPayAmt)
        tvTPHUserLogCount= findViewById(R.id.tvTPHUserLogCount)
        tvTPHUserInquiryCount= findViewById(R.id.tvTPHUserInquiryCount)
        tvTPHUserLastBookDate= findViewById(R.id.tvTPHUserLastBookDate)
        tvTMPUserPaymnetMode= findViewById(R.id.tvTMPUserPaymnetMode)
        tvTPHUserLastBookedLocation= findViewById(R.id.tvTPHUserLastBookedLocation)
        btnTPHUserBookedTotal= findViewById(R.id.btnTPHUserBookedTotal)

        val bundle = intent.extras
        user = bundle?.getSerializable("user") as User
        aemail = bundle.getString("amail").toString()
        byteArray = intent.extras?.getByteArray("image")!!

        btnTPHUserEditAccount = findViewById(R.id.btnTPHUserEditAccount)
        openerImg = findViewById(R.id.openerImg)
        openerImg.setOnClickListener {
//            val intent = Intent(this@TouristsProfileHandling, AdminHome::class.java).apply {
//                putExtra("replaceFragment", "TouristManagement")
//                putExtra("adminEmail", aemail)
//            }
//            startActivity(intent)
            finish()
        }
        btnTPHUserEditAccount.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("user", user)
                putString("amail",aemail)
                putByteArray("image", byteArray)
            }
            val intent = Intent(this@TouristsProfileHandling,TouristEditProfile::class.java)
            intent.putExtras( bundle)
            startActivity(intent)
        }
        lifecycleScope.launch(Dispatchers.Main){
            progressLoader = ProgressLoader(
                this@TouristsProfileHandling,"Fetching Details","Please Wait..."
            )
            progressLoader.startProgressLoader()

            lifecycleScope.launch(Dispatchers.IO){
                initializeTheViews(user)
            }

            delay(3000L)
            progressLoader.dismissProgressLoader() // dismiss the dialog
        }


    }

    private fun initializeTheViews(user: User) {

        lifecycleScope.launch(Dispatchers.Main) {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size ?: 0)

            simTPHUserImg.setImageBitmap(bitmap)

            btnTPHUserJoined.text = dateFormatter(user.added)
            tvTPHUserTel.text = user.tel
            tvTPHUserCountry.text = user.country?.capitalize()
            tvTPHUserName.text = "Mr " + user.uname.capitalize()


            touristDetail= emptyArray()
            lifecycleScope.launch(Dispatchers.IO) {
                // Get an instance of the TouristoDB database
                db = TouristoDB.getInstance(this@TouristsProfileHandling)
                // Get the UserDao from the database
                val userDao = db.userDao()
                val userRepo = UserRepository(userDao, Dispatchers.IO)
                touristDetail = userRepo.selectTouristManageProfile(user.uemail)!!

                // Launch a new coroutine on the Dispatchers.Main context to access the updated value of touristDetail
                lifecycleScope.launch(Dispatchers.Main) {
                    tvTPHPayDate.text = touristDetail[6]
                    if(touristDetail[6]=="No Payments"){
                        tvTMPUserPaymnetMode.text = "NA"
                    }else{
                        tvTMPUserPaymnetMode.text = "Card"
                    }
                    btnTPHUserPayAmt.text = "Rs "+touristDetail[0]
                    btnTPHUserBookedTotal.text = touristDetail[3]
                    tvTPHUserLastBookedLocation.text = touristDetail[5]
                    tvTPHUserLastBookDate.text = touristDetail[4]
                    tvTPHUserInquiryCount.text = touristDetail[2]
                    tvTPHUserLogCount.text = touristDetail[1]
                }
            }

        }
    }

    //timeconverter
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
    fun getDaySuffix(day: Int): String {
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