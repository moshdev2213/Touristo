package com.example.touristo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.touristo.Fragments.BookingFrag
import com.example.touristo.Fragments.CartFrag
import com.example.touristo.Fragments.ProfileFrag
import com.example.touristo.Fragments.UserHomeFrag
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.ConfirmationDialog
import com.example.touristo.dialogAlerts.PaySlipGenerator
import com.example.touristo.dialogAlerts.ProgressLoader
import com.example.touristo.dialogAlerts.YesNoDialog
import com.example.touristo.fragmentListeners.FragmentListenerUserIndex
import com.example.touristo.modal.Booking
import com.example.touristo.modal.User
import com.example.touristo.modal.Villa
import com.example.touristo.modalDTO.BookingDTO
import com.example.touristo.repository.BookingRepository
import com.example.touristo.repository.UserRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserIndex : AppCompatActivity(), FragmentListenerUserIndex {
    private lateinit var imgUIndexPropic:ImageView
    private lateinit var btnNav : BottomNavigationView
    private lateinit var globalEmail:String
    private lateinit var db : TouristoDB
    private lateinit var confirmationDialog: ConfirmationDialog
    private lateinit var paySlipGenerator: PaySlipGenerator
    private lateinit var yesNoDialog: YesNoDialog
    private lateinit var progressLoader: ProgressLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_index)
        // In Activity's onCreate() for instance this transparents the background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        globalEmail = intent.getStringExtra("useremail").toString()

        btnNav = findViewById(R.id.bottomNavigationView)
        replaceFragment(UserHomeFrag())
        btnNav.setOnItemSelectedListener{
            when(it.itemId){
                R.id.home ->{
                    //the if block is executed so that the notification pannel color changes and the Icon of them changes
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val flags = window.decorView.systemUiVisibility
                        window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    }
                    replaceFragment(UserHomeFrag())
                }
                R.id.cart ->{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val flags = window.decorView.systemUiVisibility
                        window.decorView.systemUiVisibility = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    }
                    replaceFragment(CartFrag())
                }
                R.id.booked ->{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val flags = window.decorView.systemUiVisibility
                        window.decorView.systemUiVisibility = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    }
                    replaceFragment(BookingFrag())
                }
                R.id.profile ->{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val flags = window.decorView.systemUiVisibility
                        window.decorView.systemUiVisibility = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    }
                    replaceFragment(ProfileFrag())
                }

                else->{

                }
            }
            true
        }

    }
    //the dbQuery fetches the UserObject
    private suspend fun getDbUserObject(email:String): User {
        val userObj : User
        // Get an instance of the TouristoDB database
        db = TouristoDB.getInstance(application)
        // Get the UserDao from the database
        val userDao = db.userDao()
        val userRepo = UserRepository(userDao, Dispatchers.IO)

        userObj = userRepo.getUserObject(email)
        return userObj
    }

    override fun deleteUserAccount() {
        val mail = globalEmail

        lifecycleScope.launch(Dispatchers.IO){
            // Get an instance of the TouristoDB database
            db = TouristoDB.getInstance(application)
            // Get the UserDao from the database
            val userDao = db.userDao()
            val userRepo = UserRepository(userDao, Dispatchers.IO)
            val result = userRepo.deleteUserAccount(mail)

            lifecycleScope.launch(Dispatchers.Main){
                progressLoader = ProgressLoader(this@UserIndex,"Deleting","Please Wait.....")
                if (result>0){
                    progressLoader.startProgressLoader()
                    delay(3000L)
                    progressLoader.dismissProgressLoader() // dismiss the dialog
                    val intent = Intent(this@UserIndex, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    //do something
                }
            }
        }


    }

    // Implement the methods from the fragmentListener interface
    override fun onFragmentPropicButtonClick() {
        var userObj : User
        GlobalScope.launch(Dispatchers.Main){
            userObj = getDbUserObject(globalEmail)

            val bundle = Bundle().apply {
                putSerializable("user", userObj)
            }
            val intent = Intent(this@UserIndex,EditProfile::class.java)
            intent.putExtras( bundle)
            startActivity(intent)
        }
    }

    override fun onFragmentFavouriteButtonClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.splashBackground)

            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        replaceFragment(BookingFrag())
    }

    override fun onItemClickedHome(villa: Villa) {
        var userObj:User

        lifecycleScope.launch(Dispatchers.IO){
            globalEmail = intent.getStringExtra("useremail").toString()
            userObj = getDbUserObject(globalEmail)

            val bundle = Bundle().apply {
                putSerializable("villa", villa)
                putSerializable("user", userObj)
            }
            val intent = Intent(this@UserIndex,Product::class.java)
            intent.putExtras( bundle)
            startActivity(intent)
        }
    }

    override fun InitiatePaySlip(bookingDTO: BookingDTO) {
        paySlipGenerator = PaySlipGenerator(this@UserIndex)
        paySlipGenerator.generateSlipDialog(bookingDTO, {
            //do anything
        },{
            val bundle = Bundle().apply {
                putSerializable("bookingDTO", bookingDTO)
            }
            val intent = Intent(this@UserIndex,UserInquiryForm::class.java)
            intent.putExtras( bundle)
            startActivity(intent)
        })
    }

    override fun getTheUserEmail(): String {
        return intent.getStringExtra("useremail").toString()
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.indexFrameLayout,fragment)
        fragmentTransaction.commit()
    }
}