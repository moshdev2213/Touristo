package com.example.touristo.modal

import androidx.room.Entity
import androidx.room.PrimaryKey

//this is the dataClass in which the user applies an inquiry to a booking placed this will be shown in the notification of the ADMINdASHBOARD
@Entity(tableName = "userInquiry")
data class UserInquery(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var uemail:String,
    var description:String,
    var bookingId:Int,
    var added:String,
    var modified:String?=null
)
