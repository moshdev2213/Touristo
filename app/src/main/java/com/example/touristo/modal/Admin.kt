package com.example.touristo.modal

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "admin")
data class Admin(
    @PrimaryKey(autoGenerate = true)
    var aid:Int,
    var fname:String,
    var lname:String,
    var aemail:String,
    var password:String,
    var propic:String,
    var tel:String,
    var age:Int,
    var gender:String,
    var designation:String,
    var added:Long,
    var modified:Long
)
