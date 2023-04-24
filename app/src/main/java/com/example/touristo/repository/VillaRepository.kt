package com.example.touristo.repository

import com.example.touristo.dao.VillaDao
import com.example.touristo.modal.Villa
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VillaRepository(private val dao: VillaDao, private val ioDispatcher: CoroutineDispatcher) {
    private val villaRepositoryScope = CoroutineScope(ioDispatcher)

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
}