package com.example.touristo.repository

import com.example.touristo.dao.VillaDao
import com.example.touristo.modal.Villa
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class VillaRepository(private val dao: VillaDao, private val ioDispatcher: CoroutineDispatcher) {
    private val villaRepositoryScope = CoroutineScope(ioDispatcher)

    fun getAllVilla(): List<Villa> {
        return dao.getAllVilla()
    }

    suspend fun insertListOfVilla(villa:List<Villa>){
        dao.insertListOfVilla(villa)
    }
}