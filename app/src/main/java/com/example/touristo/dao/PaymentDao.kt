package com.example.touristo.dao

import androidx.room.*
import com.example.touristo.modal.Payment

@Dao
interface PaymentDao {
    @Insert
    suspend fun insertPayment(payment: Payment)

    @Update
    suspend fun updatePayment(payment: Payment)

    @Delete
    suspend fun deletePayment(payment: Payment)

    @Query("SELECT * FROM payment")
    fun getAllPayment(): List<Payment>

    @Query("SELECT id FROM payment WHERE cardName=:cardName AND cardNumber=:cardNumber AND expireMonth=:expireMonth AND expireYear=:expireYear ORDER BY id DESC LIMIT 1\n")
    fun getPaymentId(cardName:String,cardNumber:String,expireMonth:Int,expireYear:Int):Int
}