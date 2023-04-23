package com.example.touristo.fragmentListeners

import com.example.touristo.modal.Booking
import com.example.touristo.modal.Villa

interface FragmentListenerUserIndex {
    fun onFragmentPropicButtonClick()

    fun onFragmentFavouriteButtonClick()
    fun onItemClickedHome(villa: Villa)
}