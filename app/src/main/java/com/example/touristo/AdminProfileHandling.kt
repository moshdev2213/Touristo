package com.example.touristo

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.modal.Admin
import com.example.touristo.modal.User
import com.example.touristo.repository.AdminRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AdminProfileHandling : AppCompatActivity() {
    private lateinit var btnAdminPHEditACT:Button
    private lateinit var btnAdminPHJoinedDate:Button
    private lateinit var tvAdminPHGender:TextView
    private lateinit var tvAdminPHTel:TextView
    private lateinit var tvAdminPHLogCount:TextView
    private lateinit var tvAdminPHInqCount:TextView
    private lateinit var btnAdminPHSalaryAmt:Button
    private lateinit var tvAdminPHJoinedYM:TextView
    private lateinit var tvAdminPHJoinedDate:TextView
    private lateinit var btnAdminPHDesignation:TextView
    private lateinit var simgAdminPHPic:ImageView
    private lateinit var tvAdminPHName:TextView
    private lateinit var openerImg:ImageView

    private lateinit var admin:Admin
    private lateinit var aemail:String
    private lateinit var db:TouristoDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_profile_handling)

        //the if block is executed so that the notification pannel color changes and the Icon of them changes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.bgBackground)

            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        //coe starts here
        openerImg = findViewById(R.id.openerImg)
        tvAdminPHName = findViewById(R.id.tvAdminPHName)
        simgAdminPHPic= findViewById(R.id.simgAdminPHPic)
        btnAdminPHDesignation= findViewById(R.id.btnAdminPHDesignation)
        tvAdminPHJoinedDate= findViewById(R.id.tvAdminPHJoinedDate)
        tvAdminPHJoinedYM= findViewById(R.id.tvAdminPHJoinedYM)
        btnAdminPHSalaryAmt= findViewById(R.id.btnAdminPHSalaryAmt)
        tvAdminPHInqCount= findViewById(R.id.tvAdminPHInqCount)
        tvAdminPHLogCount= findViewById(R.id.tvAdminPHLogCount)
        tvAdminPHTel= findViewById(R.id.tvAdminPHTel)
        tvAdminPHGender= findViewById(R.id.tvAdminPHGender)
        btnAdminPHJoinedDate= findViewById(R.id.btnAdminPHJoinedDate)
        btnAdminPHEditACT= findViewById(R.id.btnAdminPHEditACT)

        val bundle = intent.extras
        admin = bundle?.getSerializable("staff") as Admin
        aemail = bundle.getString("amail").toString()

        btnAdminPHEditACT.setOnClickListener {
            finish()
        }
        openerImg.setOnClickListener {
            finish()
        }

        if(admin.designation=="Admin"){
            btnAdminPHSalaryAmt.text = "Rs 90000.00"
        }else if(admin.designation=="Employee"){
            btnAdminPHSalaryAmt.text = "Rs 45000.00"
        }else if(admin.designation=="Staff"){
            btnAdminPHSalaryAmt.text = "Rs 60000.00"
        }

        tvAdminPHName.text = admin.fname.capitalize()
        btnAdminPHDesignation.text = admin.designation
        tvAdminPHJoinedYM.text = dateFormatter(admin.added)
        tvAdminPHJoinedDate.text = getOnlyDate(admin.added)
        tvAdminPHTel.text = admin.tel
        tvAdminPHGender.text=admin.gender
        btnAdminPHJoinedDate.text  = admin.aemail
        lifecycleScope.launch(Dispatchers.IO){
            getAdminLogs(admin.aemail)
        }
    }
    //getDbcredentials for the admin
    private suspend fun getAdminLogs(email:String){
        db = TouristoDB.getInstance(application)
        // Get the adminDao from the database
        val adminDao = db.adminDao()
        val adminRepo = AdminRepository(adminDao, Dispatchers.IO)
        val arrayAdmin = adminRepo.getAdminLogs(email)

        lifecycleScope.launch(Dispatchers.Main){
            tvAdminPHInqCount.text = arrayAdmin?.get(1).toString()
            tvAdminPHLogCount.text = arrayAdmin?.get(0).toString()
        }
    }
    //getOnlyDAte
    private fun getOnlyDate(date:String):String{
        val dateString = date
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
        val date = inputFormat.parse(dateString)

        val outputFormat = SimpleDateFormat("dd", Locale.getDefault())
        val day = outputFormat.format(date)
        return day

    }
    private fun dateFormatter(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())

// Convert input string to date
        val date = inputFormat.parse(date)
        // Extract day of month from parsed date
        val dayOfMonth = date.day
        val outputFormat = SimpleDateFormat("MMM yy", Locale.getDefault())

// Format date to output string
        val outputDateString = outputFormat.format(date)
        return outputDateString.toString()

    }

}