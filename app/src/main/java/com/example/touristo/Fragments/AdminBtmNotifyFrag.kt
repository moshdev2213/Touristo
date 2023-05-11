package com.example.touristo.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touristo.R
import com.example.touristo.adapter.AdminHomeINAdapter
import com.example.touristo.adapter.UserHomeBLAdapter
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.AdminInqueryModal
import com.example.touristo.modal.UserInquery
import com.example.touristo.modalDTO.BookingDTO
import com.example.touristo.repository.BookingRepository
import com.example.touristo.repository.InquiryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminBtmNotifyFrag : Fragment() {
    private lateinit var rvAdminNotifyFrag:RecyclerView
    private lateinit var tvTotalCountOfNotifications:TextView
    private lateinit var btnCountNotify:Button
    private lateinit var adapter:AdminHomeINAdapter
    private lateinit var db:TouristoDB
    private lateinit var adminInqueryModal:AdminInqueryModal
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_admin_btm_notify, container, false)
        btnCountNotify = view.findViewById(R.id.btnCountNotify)
        tvTotalCountOfNotifications = view.findViewById(R.id.tvTotalCountOfNotifications)
        rvAdminNotifyFrag = view.findViewById(R.id.rvAdminNotifyFrag)
        initRecyclerView()
        return view
    }

    fun initRecyclerView(){
        lifecycleScope.launch(Dispatchers.IO) {
            // Get an instance of the TouristoDB database
            db = TouristoDB.getInstance(requireContext().applicationContext)
            val inquiryDao = db.inquiryDao()
            val inquiryRepo = InquiryRepository(inquiryDao, Dispatchers.IO)

            val count = inquiryRepo.getAllInqueryCount()
            btnCountNotify.text = count.toString()
            tvTotalCountOfNotifications.text = "You Have "+count.toString()+" Notifications"

            rvAdminNotifyFrag.layoutManager = LinearLayoutManager(context)
            adapter = AdminHomeINAdapter(requireActivity()) {
                initiateCardClick(it)
            }

            lifecycleScope.launch(Dispatchers.Main){
                //herachical touch error then put inside the lifecyclescope
                rvAdminNotifyFrag.adapter = adapter
                adapter.setList(inquiryRepo.getAllInquiry())
            }

        }
    }

    private fun initiateCardClick(it: UserInquery) {
        adminInqueryModal = AdminInqueryModal(requireActivity())
        adminInqueryModal.showModal(it.uemail,it.description,it.added) {
            //doanything
        }
    }
}