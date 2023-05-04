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
import com.example.touristo.adapter.AdminHomeVMAdapter
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.modal.Admin
import com.example.touristo.modal.Villa
import com.example.touristo.repository.AdminRepository
import com.example.touristo.repository.VillaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VillaManagement : Fragment() {
    private lateinit var btnAddVilla: Button
    private lateinit var globalEmail:String
    private lateinit var db: TouristoDB
    private lateinit var rvVillaManagement: RecyclerView
    private lateinit var adapter: AdminHomeVMAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_villa_management, container, false)
        val adminHomeActivity = requireActivity() as AdminHome
        globalEmail = adminHomeActivity.getTheAdminEmail()

        btnAddVilla = view.findViewById(R.id.btnAddVilla)
        btnAddVilla.setOnClickListener {
            val intent = Intent(adminHomeActivity, VillaProfileHandling::class.java)
            intent.putExtra("amail",globalEmail)
            startActivity(intent)
        }


        initRecyclerView(view)

        return view
    }

    private fun initRecyclerView(view: View) {
        rvVillaManagement = view.findViewById(R.id.rvVillaManagement)
        rvVillaManagement.layoutManager = LinearLayoutManager(requireActivity())
        adapter = AdminHomeVMAdapter(
            requireActivity(),
            {
                    villa: Villa ->villaProfileView(villa)
            },
            {
                    villa: Villa ->villaCardClicked(villa)
            }
        )
        rvVillaManagement.adapter = adapter
        // Get an instance of the TouristoDB database
        db = TouristoDB.getInstance(requireActivity())

        // Get the UserDao from the database
        val villaDao = db.villaDao()
        val villaRepo = VillaRepository(villaDao, Dispatchers.IO)

        lifecycleScope.launch(Dispatchers.IO){
            val villaList = villaRepo.getAllVilla()
            lifecycleScope.launch (Dispatchers.Main){
                adapter.setList(villaList)
            }
        }
    }

    private fun villaCardClicked(villa: Villa) {
        val bundle = Bundle().apply {
            putSerializable("villa", villa)
            putString("amail",globalEmail)
        }
        val intent = Intent(requireActivity(), VillaSingleView::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun villaProfileView(villa: Villa) {
        val bundle = Bundle().apply {
            putSerializable("villa", villa)
            putString("amail",globalEmail)
        }
        val intent = Intent(requireActivity(),VillaEditProfile::class.java)
        intent.putExtras( bundle)
        startActivity(intent)
    }


}