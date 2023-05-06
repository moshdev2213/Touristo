package com.example.touristo

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.touristo.Fragments.*
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.ProgressLoader
import com.example.touristo.fragmentListeners.AdminHomeFragListners
import com.example.touristo.modal.Admin
import com.example.touristo.repository.AdminRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdminHome : AppCompatActivity(), AdminHomeFragListners {
lateinit var toggle : ActionBarDrawerToggle
lateinit var drawerLayout : DrawerLayout
private lateinit var btnNav : BottomNavigationView
private lateinit var globalMail : String
private lateinit var admin : Admin
private lateinit var admHomeProImg : ImageView
private lateinit var progressLoader: ProgressLoader

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
                   lifecycleScope.launch(Dispatchers.Main){
                       progressLoader = ProgressLoader(
                           this@AdminHome,"Logging Out",
                           "Please Wait......"
                       )
                       progressLoader.startProgressLoader()
                       val intent = Intent(this@AdminHome, MainActivity::class.java)
                       drawerLayout.closeDrawers()
                       delay(3000L)
                       progressLoader.dismissProgressLoader()
                       startActivity(intent)
                       finish()
                   }
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

        //code starts here
        globalMail =  intent.getStringExtra("adminEmail").toString()
        val replaceFragment = intent.getStringExtra("replaceFragment")

        if (replaceFragment == "TouristManagement") {
            replaceAdmBottomNavFragment(TouristManagement())
        }else if(replaceFragment=="AdminManagement"){
            replaceAdmBottomNavFragment(AdminManagement())
        }



        // Get an instance of the TouristoDB database
        val db = TouristoDB.getInstance(this@AdminHome)
        //getting dbInstance of the DAo
        val adminDao = db.adminDao()
        var adminRepo = AdminRepository(adminDao,Dispatchers.IO)

        lifecycleScope.launch(Dispatchers.IO){
           admin = adminRepo.getAdminByEmail(globalMail)
            initSideNavViews(admin)
        }

    }


    //the below function is for the frag replacement in the sidenavigation bar
    fun replaceFrags(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        drawerLayout.closeDrawers()
        fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,R.anim.enter_left_to_right,R.anim.exit_left_to_right)
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

    //listnersOverriders
    fun getTheAdminEmail(): String {
        return intent.getStringExtra("adminEmail").toString()
    }
    fun initSideNavViews(admin: Admin){
        val navigationView = findViewById<NavigationView>(R.id.navView)
        val headerView = navigationView.getHeaderView(0)

        val nameTextView = headerView.findViewById<TextView>(R.id.sideNavName)
        nameTextView.text = admin.fname.capitalize()

        val emailTextView = headerView.findViewById<TextView>(R.id.sideNavEmail)
        emailTextView.text = admin.aemail.toLowerCase()
    }
}