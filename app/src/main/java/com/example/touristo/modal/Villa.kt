package com.example.touristo.modal

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "villa")
data class Villa(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var villaName:String,
    var price:Double,
    var roomType:String,
    var rating:Int,
    var description:String,
    var district:String,
    var province:String,
    val img01:String,
    val img02:String,
    val img03:String,
    val img04:String,
    var added:Long,
    var modified:Long
): Serializable
