package com.example.touristo.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.touristo.AdminHome
import com.example.touristo.R
import com.example.touristo.TouristManagementRegistration
import com.example.touristo.dbCon.TouristoDB
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


class TouristManagement : Fragment() {
    private lateinit var rvFragTM:RecyclerView
    private lateinit var fragTMAddTouristBtn:Button
    private lateinit var fragTMInactiveAct:TextView
    private lateinit var fragTMActive:TextView
    private lateinit var fragTMUpdatedDate:TextView
    private lateinit var db:TouristoDB
    val currentDateTime = Timestamp(System.currentTimeMillis())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tourist_management, container, false)
        val adminHomeActivity = requireActivity() as AdminHome
        fragTMAddTouristBtn = view.findViewById(R.id.fragTMAddTouristBtn)
        fragTMAddTouristBtn.setOnClickListener {
            val intent = Intent(adminHomeActivity,TouristManagementRegistration::class.java)
            startActivity(intent)
        }
        fragTMUpdatedDate = view.findViewById(R.id.fragTMUpdatedDate)
        fragTMUpdatedDate.text = "Updated "+dateFormatter(currentDateTime.toString())
        return view
    }

    fun dateFormatter(date:String):String{
        val date = Date() // replace with your date object
        val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val formattedDate = sdf.format(currentDateTime)
        return formattedDate
    }

}