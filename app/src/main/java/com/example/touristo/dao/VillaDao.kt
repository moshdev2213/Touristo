package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.Admin
import com.example.touristo.modal.Villa

@Dao
interface VillaDao {
    @Insert
    suspend fun insertUser(villa: Villa)

    @Update
    suspend fun updateUser(villa: Villa)

    @Delete
    suspend fun deleteUser(villa: Villa)

    @Query("SELECT * FROM admin")
    fun getAllUsers(): LiveData<List<Villa>>
}