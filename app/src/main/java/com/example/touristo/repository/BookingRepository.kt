package com.example.touristo.repository

import com.example.touristo.Payment
import com.example.touristo.dao.BookingDao
import com.example.touristo.modal.Booking
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookingRepository(private val dao: BookingDao,private val ioDispatcher: CoroutineDispatcher) {
    private val bookingRepositoryScope = CoroutineScope(ioDispatcher)

    fun insertBooking(booking: Booking){
        bookingRepositoryScope.launch(Dispatchers.IO) {
            dao.insertBooking(booking)
        }
    }
}