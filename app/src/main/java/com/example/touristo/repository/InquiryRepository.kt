package com.example.touristo.repository

import com.example.touristo.dao.InquiryDao
import com.example.touristo.modal.UserInquery
import com.example.touristo.modalDTO.BookingDTO
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

}