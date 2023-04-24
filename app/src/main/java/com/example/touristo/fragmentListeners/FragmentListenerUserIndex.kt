package com.example.touristo.fragmentListeners

import com.example.touristo.modal.Booking
import com.example.touristo.modal.Villa
import com.example.touristo.modalDTO.BookingDTO

interface FragmentListenerUserIndex {
    fun onFragmentPropicButtonClick()

    fun onFragmentFavouriteButtonClick()
    fun onItemClickedHome(villa: Villa)

    fun onBookingItemClicked(bookingDTO: BookingDTO)
}