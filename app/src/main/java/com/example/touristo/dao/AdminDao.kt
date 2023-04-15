package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.Admin
import com.example.touristo.modal.User
@Dao
interface AdminDao {
    @Insert
    suspend fun insertUser(admin: Admin)

    @Update
    suspend fun updateUser(admin: Admin)

    @Delete
    suspend fun deleteUser(admin: Admin)

    @Query("SELECT * FROM admin")
    fun getAllUsers(): LiveData<List<Admin>>
}