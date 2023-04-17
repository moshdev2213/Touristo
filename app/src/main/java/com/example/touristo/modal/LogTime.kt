package com.example.touristo.modal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logtime")
data class LogTime (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var email:String,
    var role:String,
    var log:String,
)