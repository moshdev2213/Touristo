package com.example.touristo.modalDTO

import com.example.touristo.Payment

data class BookingDTO(
    private var booked:String,
    private var paymentId:Int,
    private var img01:String,
    private var price:Double,
    private var villaName:String,
    private var villaId:Int,
    private val uemail:String,
    private var bookingId:Int
)
