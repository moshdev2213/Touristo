package com.example.touristo.modal

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "favourite")
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var uemail:String,
    var villaId:Int,
    var added:Long,
    var modified:Long
)
