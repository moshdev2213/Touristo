package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.Favourite

@Dao
interface FavouriteDao {
    @Insert
    suspend fun insertUser(favourite: Favourite)

    @Update
    suspend fun updateUser(favourite: Favourite)

    @Delete
    suspend fun deleteUser(favourite: Favourite)

    @Query("SELECT * FROM admin")
    fun getAllUsers(): LiveData<List<Favourite>>
}