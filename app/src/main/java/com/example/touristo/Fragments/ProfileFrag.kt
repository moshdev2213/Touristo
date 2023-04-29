package com.example.touristo.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.example.touristo.EditProfile
import com.example.touristo.MainActivity
import com.example.touristo.R
import com.example.touristo.dialogAlerts.ConfirmationDialog
import com.example.touristo.dialogAlerts.ProgressLoader
import com.example.touristo.fragmentListeners.FragmentListenerUserIndex
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileFrag : Fragment() {
    private lateinit var cvEditProfile:CardView
    private lateinit var cvAboutUs:CardView
    private lateinit var cvLogout:CardView
    private lateinit var cvDeleteAccount:CardView
    private lateinit var progressLoader: ProgressLoader
    private lateinit var confirmationDialog: ConfirmationDialog
    private var fragmentListener: FragmentListenerUserIndex? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)

        //initializing the views
        cvEditProfile = view.findViewById(R.id.cvEditProfile)
        cvAboutUs = view.findViewById(R.id.cvAboutUs)
        cvLogout = view.findViewById(R.id.cvLogout)
        cvDeleteAccount = view.findViewById(R.id.cvDeleteAccount)

        cvEditProfile.setOnClickListener {
            fragmentListener?.onFragmentPropicButtonClick()
        }

        cvLogout.setOnClickListener {
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

    // the below method is for communication from parent activity to fragments
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentListenerUserIndex) {
            fragmentListener = context
        } else {
            throw RuntimeException("$context must implement FragmentListenerUserIndex")
        }
    }
}