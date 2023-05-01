package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.Admin
import com.example.touristo.modal.LogTime
import com.example.touristo.modal.User
@Dao
interface AdminDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdmin(admin: Admin)

    @Update
    suspend fun updateAdmin(admin: Admin)

    @Delete
    suspend fun deleteAdmin(admin: Admin)

    @Query("SELECT * FROM admin")
    fun getAllAdmin(): LiveData<List<Admin>>

    @Query("SELECT COUNT(aid) FROM admin WHERE aemail=:email ")
    fun getUserExist(email: String):Int

    @Query("SELECT COUNT(aid) FROM admin WHERE aemail=:email AND password=:password")
    fun getUserLogin( email: String, password:String):Int

    @Insert
    suspend fun insertLoggedTime(logTime: LogTime)
}