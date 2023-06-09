package com.example.touristo.repository

import com.example.touristo.dao.FavouriteDao
import com.example.touristo.modal.Favourite
import com.example.touristo.modalDTO.FavouriteDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteRepository(private val dao: FavouriteDao, private val ioDispatcher: CoroutineDispatcher) {
    private val favouriteRepositoryScope = CoroutineScope(ioDispatcher)

    fun insertFavourites(favourite: Favourite){
        favouriteRepositoryScope.launch {
            dao.insertFavourites(favourite)
        }
    }
    suspend fun getUserAddedOrNot(email:String, id:Int):Int{
        var result =-1
        withContext(ioDispatcher){
            result = dao.getUserAddedOrNot(email,id)
        }
        println(result)
        return result
    }
    suspend fun getAllFavouriteList(email:String):List<FavouriteDTO>{
        var favouriteDTO:List<FavouriteDTO>
        withContext(ioDispatcher){
            favouriteDTO = dao.getAllFavouriteList(email)
        }
        return favouriteDTO
    }

    suspend fun deleteItemFromBookingList(id:Int):Int{
        var result : Int = -1
        withContext(ioDispatcher){
            dao.deleteItemFromBookingList(id)
            result =2
        }
        return result
    }
}