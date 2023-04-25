package com.example.touristo.repository

import com.example.touristo.dao.PaymentDao
import kotlinx.coroutines.*

class PaymentRepository(private val dao: PaymentDao,private val ioDispatcher: CoroutineDispatcher) {
    private val paymentRepositoryScope = CoroutineScope(ioDispatcher)

    fun insertPayment(payment: com.example.touristo.modal.Payment){
        paymentRepositoryScope.launch(Dispatchers.IO) {
            dao.insertPayment(payment)
        }
    }
    suspend fun getPaymentId(cardName:String, cardNumber:String, expireMonth:Int, expireYear:Int):Int{
        var userExists = -1
        withContext(ioDispatcher) {
            userExists = dao.getPaymentId(cardName,cardNumber,expireMonth,expireYear)
        }
        return userExists
    }
}