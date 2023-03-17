package com.example.touristo

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.touristo.Fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class AdminHome : AppCompatActivity() {
lateinit var toggle : ActionBarDrawerToggle
lateinit var drawerLayout : DrawerLayout
private lateinit var btnNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        //the if block is executed so that the notification pannel color changes and the Icon of them changes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.bgBackground)

            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        drawerLayout  = findViewById(R.id.drawableLaoyout)
        val navView : NavigationView = findViewById(R.id.navView)

        val openDrawerBtn = findViewById<ImageView>(R.id.openerImg)
        openDrawerBtn.setOnClickListener {
            // If the navigation drawer is not open then open it, if its already open then close it.
            if(!drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.openDrawer(
                GravityCompat.START);
            else drawerLayout.closeDrawer(GravityCompat.END);
        }

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.openSideNav,R.string.closeSideNav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        replaceFrags(AdminHomeFrag())
        navView.setNavigationItemSelectedListener {
            it.isChecked = true
            when(it.itemId){
                R.id.nav_home->replaceFrags(AdminHomeFrag())
                R.id.nav_customerManagement->replaceFrags(TouristManagement())
                R.id.nav_adminManagement->replaceFrags(AdminManagement())
                R.id.nav_villaManagement->replaceFrags(VillaManagement())
                R.id.nav_profile->{
                    replaceAdmBottomNavFragment(AdminBtmProfileFrag())
                    drawerLayout.closeDrawers()

                }
                R.id.nav_logout->{
                    Toast.makeText(this,"Implementation In Process \uD83D\uDE0E",Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawers()
                }
            }
            true
        }
        //the below starts for the bottomNavgation parts
        btnNav = findViewById(R.id.btmnavadmin)
        btnNav.setOnItemSelectedListener{
            when(it.itemId){
                R.id.admprofile->{
                    replaceAdmBottomNavFragment(AdminBtmProfileFrag())
                }
                R.id.admnotify->{
                    replaceAdmBottomNavFragment(AdminBtmNotifyFrag())
                }
                R.id.admhome->{
                    replaceFrags(AdminHomeFrag())
                }
            }
            true
        }


    }

    //the below function is for the frag replacement in the sidenavigation bar
    fun replaceFrags(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        drawerLayout.closeDrawers()
        fragmentTransaction.commit()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //the below method replaces the fragments that are for the bottom navigation
    private fun replaceAdmBottomNavFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()


    }
}