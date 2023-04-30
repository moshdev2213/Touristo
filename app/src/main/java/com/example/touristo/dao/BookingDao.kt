package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.Booking
import com.example.touristo.modal.UserInquery
import com.example.touristo.modal.Villa
import com.example.touristo.modalDTO.BookingDTO

@Dao
interface BookingDao {
    @Insert
    suspend fun insertBooking(booking: Booking)

    @Update
    suspend fun updateBooking(booking: Booking)

    @Delete
    suspend fun deleteBooking(booking: Booking)

    @Query("SELECT * FROM booking")
    fun getAllBooking(): List<Booking>

    @Query("SELECT strftime('%d.%m.%Y %H:%M:%S', b.added) as booked,b.reference,b.paymentId,v.img01,v.price,v.villaName,v.id as villaId ,b.uemail,b.id as bookingId\n" +
            "FROM booking b, villa v\n" +
            "WHERE b.villaId = v.id and b.uemail=:email")
    fun getBookingForListView(email:String):List<BookingDTO>
}