package com.example.touristo

import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.touristo.Fragments.BookingFrag
import com.example.touristo.Fragments.CartFrag
import com.example.touristo.Fragments.ProfileFrag
import com.example.touristo.Fragments.UserHomeFrag
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserIndex : AppCompatActivity() {
    private lateinit var btnNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_index)

        //the if block is executed so that the notification pannel color changes and the Icon of them changes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.bgBackground)

            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

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
                R.id.profile ->replaceFragment(ProfileFrag())

                else->{

                }
            }
            true
        }

    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.indexFrameLayout,fragment)
        fragmentTransaction.commit()

        
    }
}