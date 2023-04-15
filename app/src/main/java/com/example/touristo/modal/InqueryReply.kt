package com.example.touristo.modal

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "inquiryReply")
data class InqueryReply(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var inqueryId:Int,
    var uemail:String,
    var aemail:String,
    var description:String,
    var bookingId:Int,
    var added:Long,
    var modified:Long
)
