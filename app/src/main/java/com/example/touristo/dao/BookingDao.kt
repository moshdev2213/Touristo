package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.Booking
import com.example.touristo.modal.UserInquery

@Dao
interface BookingDao {
    @Insert
    suspend fun insertUser(booking: Booking)

    @Update
    suspend fun updateUser(booking: Booking)

    @Delete
    suspend fun deleteUser(booking: Booking)

    @Query("SELECT * FROM booking")
    fun getAllUsers(): LiveData<List<Booking>>
}