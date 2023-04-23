package com.example.touristo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.touristo.Fragments.BookingFrag
import com.example.touristo.Fragments.CartFrag
import com.example.touristo.Fragments.ProfileFrag
import com.example.touristo.Fragments.UserHomeFrag
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.fragmentListeners.FragmentListenerUserIndex
import com.example.touristo.modal.User
import com.example.touristo.modal.Villa
import com.example.touristo.repository.UserRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserIndex : AppCompatActivity(), FragmentListenerUserIndex {
    private lateinit var imgUIndexPropic:ImageView
    private lateinit var btnNav : BottomNavigationView
    private lateinit var globalEmail:String
    private lateinit var db : TouristoDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_index)
        //the if block is executed so that the notification pannel color changes and the Icon of them changes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.bgBackground)

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
                        window.statusBarColor = ContextCompat.getColor(this, R.color.bgBackground)

                        val flags = window.decorView.systemUiVisibility
                        window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    }
                    replaceFragment(UserHomeFrag())
                }
                R.id.cart ->{
                    //the if block is executed so that the notification pannel color changes and the Icon of them changes
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.statusBarColor = ContextCompat.getColor(this, R.color.splashBackground)

                        val flags = window.decorView.systemUiVisibility
                        window.decorView.systemUiVisibility = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    }
                    replaceFragment(CartFrag())
                }
                R.id.booked ->{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.statusBarColor = ContextCompat.getColor(this, R.color.splashBackground)

                        val flags = window.decorView.systemUiVisibility
                        window.decorView.systemUiVisibility = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    }
                    replaceFragment(BookingFrag())
                }
                R.id.profile ->{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        window.statusBarColor = ContextCompat.getColor(this, R.color.splashBackground)

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
    suspend fun getDbUserObject(email:String): User {
        val userObj : User
        // Get an instance of the TouristoDB database
        db = TouristoDB.getInstance(application)
        // Get the UserDao from the database
        val userDao = db.userDao()
        val userRepo = UserRepository(userDao, Dispatchers.IO)

        userObj = userRepo.getUserObject(email)
        return userObj
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
        val bundle = Bundle().apply {
            putSerializable("villa", villa)
        }
        val intent = Intent(this@UserIndex,Product::class.java)
        intent.putExtras( bundle)
        startActivity(intent)
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.indexFrameLayout,fragment)
        fragmentTransaction.commit()
    }
}