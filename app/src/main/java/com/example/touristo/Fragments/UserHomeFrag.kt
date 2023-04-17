package com.example.touristo.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.example.touristo.R
import com.example.touristo.fragmentListeners.FragmentListenerUserIndex

class UserHomeFrag : Fragment() {

    private var fragmentListener: FragmentListenerUserIndex? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_user_home, container, false)

        // Access the views in the Fragment's layout
        val button = view.findViewById<ImageView>(R.id.imgUIndexPropic)
        button.setOnClickListener {
            fragmentListener?.onFragmentButtonClick()
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