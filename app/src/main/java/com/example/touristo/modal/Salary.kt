package com.example.touristo.modal

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "salary")
data class Salary(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var aemail:String,
    var salary:Double,
    var added:Long,
    var modified:Long
)
