package com.example.touristo.repository

import com.example.touristo.Payment
import com.example.touristo.dao.BookingDao
import com.example.touristo.modal.Booking
import com.example.touristo.modal.Villa
import com.example.touristo.modalDTO.BookingDTO
import kotlinx.coroutines.*

class BookingRepository(private val dao: BookingDao,private val ioDispatcher: CoroutineDispatcher) {
    private val bookingRepositoryScope = CoroutineScope(ioDispatcher)

    fun insertBooking(booking: Booking){
        bookingRepositoryScope.launch(Dispatchers.IO) {
            dao.insertBooking(booking)
        }
    }
    suspend fun getBookingForListView(email:String): List<BookingDTO> {
        var bookingList : List<BookingDTO>
        withContext(ioDispatcher){
            bookingList = dao.getBookingForListView(email)
        }
       return bookingList
    }
}