package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.Payment
import com.example.touristo.modal.Villa

@Dao
interface PaymentDao {
    @Insert
    suspend fun insertUser(payment: Payment)

    @Update
    suspend fun updateUser(payment: Payment)

    @Delete
    suspend fun deleteUser(payment: Payment)

    @Query("SELECT * FROM admin")
    fun getAllUsers(): LiveData<List<Payment>>
}