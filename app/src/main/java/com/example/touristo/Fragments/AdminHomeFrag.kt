package com.example.touristo.Fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.touristo.AdminHome
import com.example.touristo.R
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.fragmentListeners.AdminHomeFragListners
import com.example.touristo.fragmentListeners.FragmentListenerUserIndex
import com.example.touristo.repository.AdminHomeRepository
import com.example.touristo.repository.AdminRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AdminHomeFrag : Fragment() {

    private var fragmentListener: AdminHomeFragListners? = null
    private lateinit var adminFragHomeName:TextView
    private lateinit var adminHomeFragsales:TextView

    private lateinit var adminHomeFragStatsSales:TextView
    private lateinit var adminHomeFragBookCount:TextView
    private lateinit var adminHomeFragAdminCout:TextView
    private lateinit var adminHomeFragCusCount:TextView

    private lateinit var btnAdminHomeFragSeeCus:Button

    private lateinit var adminHomeFragObj1Price:TextView
    private lateinit var adminHomeFragObj1Name:TextView
    private lateinit var adminHomeFragObj1SubName:TextView
    private lateinit var admnHomeGragObj01Pic:ImageView

    private lateinit var adminHomeFragObj2Price:TextView
    private lateinit var adminHomeFragObj2Name:TextView
    private lateinit var adminHomeFragObj2SubName:TextView
    private lateinit var adminHomeFragObj02Pic:ImageView
    private lateinit var btnAdminHomeFragSeeBookings:Button

    lateinit var globalEmail:String
    private lateinit var adminRepository:AdminHomeRepository

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_home, container, false)
        val adminHomeActivity = requireActivity() as AdminHome

        globalEmail = adminHomeActivity.getTheAdminEmail()

        // Get an instance of the TouristoDB database
        val db = TouristoDB.getInstance(adminHomeActivity)
        //getting dbInstance of the DAo
        val adminDao = db.adminDao()
        val bookingDao = db.bookingDao()
        val paymentDao = db.paymentDao()
        val userDao = db.userDao()
        adminRepository = AdminHomeRepository(
            adminDao,
            paymentDao,
            bookingDao,
            userDao,
            Dispatchers.IO
        )
        btnAdminHomeFragSeeCus = view.findViewById(R.id.btnAdminHomeFragSeeCus)
        btnAdminHomeFragSeeBookings = view.findViewById(R.id.btnAdminHomeFragSeeBookings)
        btnAdminHomeFragSeeCus.setOnClickListener {
            adminHomeActivity.replaceFrags(TouristManagement())
        }
        btnAdminHomeFragSeeBookings.setOnClickListener {
            adminHomeActivity.replaceFrags(VillaManagement())
        }
        getCard01and02Details(view)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCard01and02Details(view: View){

        adminFragHomeName = view.findViewById(R.id.adminFragHomeName)
        adminHomeFragAdminCout = view.findViewById(R.id.adminHomeFragAdminCout)
        adminHomeFragsales = view.findViewById(R.id.adminHomeFragsales)
        adminHomeFragCusCount = view.findViewById(R.id.adminHomeFragCusCount)
        adminHomeFragBookCount = view.findViewById(R.id.adminHomeFragBookCount)
        adminHomeFragStatsSales = view.findViewById(R.id.adminHomeFragStatsSales)

        lifecycleScope.launch(Dispatchers.IO){
            val result = adminRepository.getAdminInfo(globalEmail)
            lifecycleScope.launch(Dispatchers.Main){
                if(result!=null){
                    adminFragHomeName.text = "Hello Mr."+result[0]+" \uD83D\uDE0E"
                    adminHomeFragAdminCout.text = result[1]
                    adminHomeFragsales.text = formatDate(result[2])
                    adminHomeFragStatsSales.text = result[3]
                    adminHomeFragBookCount.text = result[4]
                    adminHomeFragCusCount.text = result[5]
                }
            }
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yy hh:mm:ssa", Locale.ENGLISH)
        val dateTime = LocalDateTime.parse(dateString, inputFormatter)
        return outputFormatter.format(dateTime)
    }


    // the below method is for communication from parent activity to fragments
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AdminHomeFragListners) {
            fragmentListener = context
        } else {
            throw RuntimeException("$context must implement AdminHomeFragListners")
        }
    }
}