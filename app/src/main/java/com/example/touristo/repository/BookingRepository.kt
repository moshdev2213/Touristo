package com.example.touristo.repository

import com.example.touristo.Payment
import com.example.touristo.dao.BookingDao
import com.example.touristo.modal.Booking
import com.example.touristo.modal.Villa
import com.example.touristo.modalDTO.BookingDTO
import kotlinx.coroutines.*

class BookingRepository(private val dao: BookingDao,private val ioDispatcher: CoroutineDispatcher) {
    private val bookingRepositoryScope = CoroutineScope(ioDispatcher)
    lateinit var bookingList : List<BookingDTO>
    fun insertBooking(booking: Booking){
        bookingRepositoryScope.launch(Dispatchers.IO) {
            dao.insertBooking(booking)
        }
    }

    suspend fun deleteItemFromBookingList(id:Int):Int{
        var result : Int = -1
        withContext(ioDispatcher){
            dao.deleteItemFromBookingList(id)
            result =2
        }
        return result
    }
    suspend fun getBookingForListView(email:String): List<BookingDTO> {

        withContext(ioDispatcher){
            bookingList = dao.getBookingForListView(email)
        }
       return bookingList
    }
}