package com.example.touristo.modalDTO

import com.example.touristo.Payment

data class BookingDTO(
    var booked:String,
    var paymentId:Int,
    var img01:String,
    var price:Double,
    var villaName:String,
    var villaId:Int,
    val uemail:String,
    var bookingId:Int
)
