package com.example.touristo.repository

import com.example.touristo.dao.VillaDao
import com.example.touristo.modal.Admin
import com.example.touristo.modal.Villa
import kotlinx.coroutines.*

class VillaRepository(private val dao: VillaDao, private val ioDispatcher: CoroutineDispatcher) {
    private val villaRepositoryScope = CoroutineScope(ioDispatcher)

    suspend fun insertVilla(villa: Villa){
        villaRepositoryScope.launch {
            dao.insertVilla(villa)
        }
    }
    suspend fun getAllVilla(): List<Villa> {
        var result :List<Villa>
        withContext(ioDispatcher){
            result =dao.getAllVilla()
        }
        return result

    }

    suspend fun insertListOfVilla(villa:List<Villa>){
        villaRepositoryScope.launch {
            dao.insertListOfVilla(villa)
        }

    }

    fun deleteVilla(villa: Villa){
        villaRepositoryScope.launch(Dispatchers.IO) {
            dao.deleteVilla(villa)
        }
    }
    suspend fun updateVillaProfile(
        villaName:String, price:String, roomType:String, rating:String, description: String, district: String,
        province:String, img01: String,img02: String,img03: String,img04: String,modified:String,id:Int):Int{
        var result = 0
        withContext(ioDispatcher) {
            result = dao.updateVillaProfile(villaName, price, roomType,rating,description, district, province, img01, img02, img03, img04,modified,id)
        }
        return result
    }

    suspend fun getVillaBookCount(id:Int):Int{
        var result = -1
        withContext(ioDispatcher) {
            result=dao.getVillaBookCount(id)
        }

        return result
    }
}