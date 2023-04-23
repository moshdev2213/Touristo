package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.Admin
import com.example.touristo.modal.Villa

@Dao
interface VillaDao {
    @Insert
    suspend fun insertVilla(villa: Villa)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfVilla(villa: List<Villa>)

    @Update
    suspend fun updateVilla(villa: Villa)

    @Delete
    suspend fun deleteVilla(villa: Villa)

    @Query("SELECT * FROM villa")
    fun getAllVilla(): List<Villa>
}