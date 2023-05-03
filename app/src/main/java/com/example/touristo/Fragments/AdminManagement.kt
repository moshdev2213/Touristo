package com.example.touristo.Fragments

import android.content.Intent
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
import com.example.touristo.adapter.AdminHomeSMAdapter
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.modal.Admin
import com.example.touristo.repository.AdminRepository
import com.example.touristo.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminManagement : Fragment() {
    private lateinit var btnStaffMAddStaff:Button
    private lateinit var globalEmail:String
    private lateinit var db:TouristoDB
    private lateinit var rvFragSM:RecyclerView
    private lateinit var adapter:AdminHomeSMAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_management, container, false)

        val adminHomeActivity = requireActivity() as AdminHome
        globalEmail = adminHomeActivity.getTheAdminEmail()

        btnStaffMAddStaff = view.findViewById(R.id.btnStaffMAddStaff)
        btnStaffMAddStaff.setOnClickListener {
            val intent = Intent(adminHomeActivity,RegisterAdmin::class.java)
            intent.putExtra("amail",globalEmail)
            startActivity(intent)
        }


        initRecyclerView(view)
        return view
    }

    private fun initRecyclerView(view: View) {
        rvFragSM = view.findViewById(R.id.rvFragSM)
        rvFragSM.layoutManager = LinearLayoutManager(requireActivity())
        adapter = AdminHomeSMAdapter(
            requireActivity(),
            {
                admin:Admin->staffProfileView(admin)
            },
            {
                admin:Admin->staffCardClicked(admin)
            }
        )
        rvFragSM.adapter = adapter
        // Get an instance of the TouristoDB database
        db = TouristoDB.getInstance(requireActivity())

        // Get the UserDao from the database
        val adminDao = db.adminDao()
        val adminRepo = AdminRepository(adminDao, Dispatchers.IO)

        lifecycleScope.launch(Dispatchers.IO){
            val adminList = adminRepo.getAllAdmin()
            lifecycleScope.launch (Dispatchers.Main){
                adapter.setList(adminList)
            }
        }

    }

    private fun staffCardClicked(admin: Admin) {
        val bundle = Bundle().apply {
            putSerializable("staff", admin)
            putString("amail",globalEmail)
        }
        val intent = Intent(requireActivity(),AdminProfileHandling::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun staffProfileView(admin: Admin) {
        val bundle = Bundle().apply {
            putSerializable("admin", admin)
            putString("amail",globalEmail)
        }
        val intent = Intent(requireActivity(),AdminUpdateProfile::class.java)
        intent.putExtras( bundle)
        startActivity(intent)
    }

    private fun getAdminObjectForIntent(email:String): Admin? {
       var adminObj: Admin? = null
        lifecycleScope.launch(Dispatchers.IO){
            db = TouristoDB.getInstance(requireActivity())
            val adminDao= db.adminDao()
            val adminRepo = AdminRepository(adminDao,Dispatchers.IO)
            adminObj = adminRepo.getAdminByEmail(email)

        }
        return adminObj
    }
}