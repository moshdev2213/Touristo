package com.example.touristo.Fragments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touristo.*
import com.example.touristo.adapter.AdminHomeTMAdapter
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.ProgressLoader
import com.example.touristo.modal.User
import com.example.touristo.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


class TouristManagement : Fragment() {
    private lateinit var rvFragTM:RecyclerView
    private lateinit var adapter: AdminHomeTMAdapter
    private lateinit var fragTMAddTouristBtn:Button
    private lateinit var db:TouristoDB
    private lateinit var byteArray:ByteArray

    val currentDateTime = Timestamp(System.currentTimeMillis())
    private  lateinit var globalEmail :String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tourist_management, container, false)
        val adminHomeActivity = requireActivity() as AdminHome
        globalEmail = adminHomeActivity.getTheAdminEmail()

        fragTMAddTouristBtn = view.findViewById(R.id.fragTMAddTouristBtn)
        fragTMAddTouristBtn.setOnClickListener {
            val intent = Intent(adminHomeActivity,TouristManagementRegistration::class.java)
            intent.putExtra("amail",globalEmail)
            startActivity(intent)
        }
        initRecyclerView(view)

        lifecycleScope.launch(Dispatchers.Main){
            val progressBuilder = ProgressLoader(requireContext(),"Fetching Details","Please Wait......")
            progressBuilder.startProgressLoader()
            delay(2000L)
            progressBuilder.dismissProgressLoader()
        }
        return view
    }

    private fun initRecyclerView(view: View){
        rvFragTM = view.findViewById(R.id.rvFragTM)
        rvFragTM.layoutManager = LinearLayoutManager(requireActivity())
        adapter = AdminHomeTMAdapter(requireActivity(),{user, bitmap ->
            userProfileView(user,bitmap)
        },{ user, bitmap ->
            // handle user card click with user and bitmap parameters
            userCardClicked(user,bitmap)
        })
        rvFragTM.adapter = adapter
        // Get an instance of the TouristoDB database
        db = TouristoDB.getInstance(requireActivity())

        // Get the UserDao from the database
        val userDao = db.userDao()
        val userRepo = UserRepository(userDao, Dispatchers.IO)

        lifecycleScope.launch(Dispatchers.IO){
            val userList = userRepo.getAllUsers()
            lifecycleScope.launch (Dispatchers.Main){
                adapter.setList(userList)
            }
        }
    }

    private fun userCardClicked(selectedItem: User,bitmap: Bitmap) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream)
        byteArray = byteArrayOutputStream.toByteArray()

        val bundle = Bundle().apply {
            putSerializable("user", selectedItem)
            putString("amail",globalEmail)
            putByteArray("image", byteArray)
        }
        val intent = Intent(requireActivity(),TouristsProfileHandling::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun userProfileView(selectedItem: User,bitmap: Bitmap) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream)
        byteArray = byteArrayOutputStream.toByteArray()
        val bundle = Bundle().apply {
            putSerializable("user", selectedItem)
            putString("amail",globalEmail)
            putByteArray("image", byteArray)
        }
        val intent = Intent(requireActivity(),TouristEditProfile::class.java)
        intent.putExtras( bundle)
        startActivity(intent)
    }

    private fun dateFormatter(date: String): String {
        Date() // replace with your date object
        val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        return sdf.format(currentDateTime)
    }

}