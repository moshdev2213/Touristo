package com.example.touristo.modal

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "payment")
data class Payment(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var cardName:String,
    var cardNumber:String,
    var expireMonth:Int,
    var expireYear:Int,
    var cvc:Int,
    var added:String,
    var modified:String?=null
)
