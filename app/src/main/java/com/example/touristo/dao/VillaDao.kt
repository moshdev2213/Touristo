package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.Admin
import com.example.touristo.modal.Villa

@Dao
interface VillaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVilla(villa: Villa)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfVilla(villa: List<Villa>)

    @Update
    suspend fun updateVilla(villa: Villa)

    @Delete
    suspend fun deleteVilla(villa: Villa)

    @Query("SELECT * FROM villa")
    fun getAllVilla(): List<Villa>

    @Query("UPDATE villa SET villaName =:villaName,price=:price, roomType=:roomType , rating=:rating , description=:description , district=:district , province=:province ,img01=:img01,img02=:img02,img03=:img03,img04=:img04,modified=:modified  WHERE id =:id;")
    fun updateVillaProfile(
        villaName:String, price:String, roomType:String, rating:String, description: String, district: String,
        province:String, img01: String,img02: String,img03: String,img04: String,modified:String,id:Int):Int
}