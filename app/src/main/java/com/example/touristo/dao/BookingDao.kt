package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.Booking
import com.example.touristo.modal.UserInquery
import com.example.touristo.modal.Villa

@Dao
interface BookingDao {
    @Insert
    suspend fun insertBooking(booking: Booking)

    @Update
    suspend fun updateBooking(booking: Booking)

    @Delete
    suspend fun deleteBooking(booking: Booking)

    @Query("SELECT * FROM booking")
    fun getAllBooking(): List<Villa>
}