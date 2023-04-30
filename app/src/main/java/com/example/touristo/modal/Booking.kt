package com.example.touristo.modal

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "booking")
data class Booking(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var uemail:String,
    var villaId:Int,
    var bookedVilaName:String?=null,
    var reference:String,
    var paymentId:Int,
    var added:String,
    var modified:String?=null

):java.io.Serializable
