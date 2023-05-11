package com.example.touristo.Fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.example.touristo.MainActivity
import com.example.touristo.R
import com.example.touristo.dbCon.TouristoDB
import com.example.touristo.dialogAlerts.ProgressLoader
import com.example.touristo.dialogAlerts.YesNoDialog
import com.example.touristo.fragmentListeners.AdminHomeFragListners
import com.example.touristo.fragmentListeners.FragmentListenerUserIndex
import com.example.touristo.modal.Admin
import com.example.touristo.modal.User
import com.example.touristo.repository.AdminRepository
import com.example.touristo.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdminBtmProfileFrag : Fragment() {

    private lateinit var cvAdminDelete:CardView
    private lateinit var cvAdminFAQ:CardView
    private lateinit var cvLogoutAdmin:CardView
    private lateinit var cvEditProfileAdmin:CardView
    private lateinit var progressLoader: ProgressLoader
    private lateinit var yesNoDialog: YesNoDialog
    private var fragmentListener: AdminHomeFragListners? = null
    private lateinit var adminEmail : String
    private lateinit var db:TouristoDB
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_admin_btm_profile, container, false)

        adminEmail = fragmentListener?.getTheAdminEmailBYInterface().toString()
        println("pkoooooooooo email eka : "+adminEmail)
        cvEditProfileAdmin = view.findViewById(R.id.cvEditProfileAdmin)
        cvAdminFAQ = view.findViewById(R.id.cvAdminFAQ)
        cvAdminDelete = view.findViewById(R.id.cvAdminDelete)
        cvLogoutAdmin = view.findViewById(R.id.cvLogoutAdmin)

        cvEditProfileAdmin.setOnClickListener {

        }
        cvAdminDelete.setOnClickListener {
            yesNoDialog = YesNoDialog(requireContext())
            yesNoDialog.yesNoConfirmDialog({
                //do anything

            },{
                lifecycleScope.launch(Dispatchers.IO){
                    deleteAdminUser(adminEmail)
                }
            })
        }
        cvAdminFAQ.setOnClickListener {

        }
        cvLogoutAdmin.setOnClickListener {
            progressLoader = context?.let { it1 ->
                ProgressLoader(
                    it1,"Logging Out","Please Wait..."
                )
            }!!
            progressLoader.startProgressLoader()


            // start a coroutine to dismiss the dialog after 5 seconds
            lifecycleScope.launch {
                delay(3000L) // delay for 5 seconds
                progressLoader.dismissProgressLoader() // dismiss the dialog
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private suspend fun deleteAdminUser(email:String){
        // Get an instance of the TouristoDB database
        db = TouristoDB.getInstance(requireActivity())
        // Get the UserDao from the database
        val adminDao = db.adminDao()
        val adminRepo = AdminRepository(adminDao, Dispatchers.IO)
        val result =adminRepo.deleteAdminByEmail(email)

        lifecycleScope.launch(Dispatchers.Main){
            progressLoader = ProgressLoader(requireActivity(),"Deleting","Please Wait.....")
            if (result>0){
                progressLoader.startProgressLoader()
                delay(3000L)
                progressLoader.dismissProgressLoader() // dismiss the dialog
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }else{
                //do something
            }
        }
    }
}