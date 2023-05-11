package com.example.touristo.repository

import com.example.touristo.dao.InquiryDao
import com.example.touristo.modal.UserInquery
import com.example.touristo.modalDTO.BookingDTO
import com.example.touristo.modalDTO.FavouriteDTO
import kotlinx.coroutines.*

class InquiryRepository(private val dao: InquiryDao, private val ioDispatcher: CoroutineDispatcher) {
    private val inquiryRepositoryScope = CoroutineScope(ioDispatcher)

    suspend  fun insertInquiry(userInquery: UserInquery):Int{
        var result : Int = -1
        withContext(ioDispatcher){
            dao.insertInquiry(userInquery)
            result=2
        }
        return result
    }

    suspend  fun deleteInquiry(userInquery: UserInquery):Int{
        var result : Int = -1
        withContext(ioDispatcher){
           result =  dao.deleteInquiry(userInquery)
        }
        return result
    }
    suspend fun getAllInquiry():List<UserInquery>{
        var userInquery:List<UserInquery>
        withContext(ioDispatcher){
            userInquery = dao.getAllInquiry()
        }
        return userInquery
    }

}