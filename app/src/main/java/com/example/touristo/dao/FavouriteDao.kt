package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.Favourite

@Dao
interface FavouriteDao {
    @Insert
    suspend fun insertFavourites(favourite: Favourite)

    @Update
    suspend fun updateUser(favourite: Favourite)

    @Delete
    suspend fun deleteUser(favourite: Favourite)

    @Query("SELECT * FROM favourite")
    fun getAllUsers(): List<Favourite>

    @Query("SELECT COUNT(id) FROM favourite WHERE uemail=:uemail AND villaId=:villaId")
    fun getUserAddedOrNot(uemail:String,villaId : Int):Int
}