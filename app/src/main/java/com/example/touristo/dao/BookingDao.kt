package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.Booking
import com.example.touristo.modal.UserInquery

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
}