package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.Favourite
import com.example.touristo.modalDTO.FavouriteDTO

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

    @Query("select strftime('%d.%m.%Y %H:%M',f.added) as added,v.villaName,v.id as villaId ,v.price,v.img01,f.uemail from villa v,favourite f where v.id=f.villaId and f.uemail =:email")
    fun getAllFavouriteList(email: String):List<FavouriteDTO>
}