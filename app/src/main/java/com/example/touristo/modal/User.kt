package com.example.touristo.modal

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var uid:Int,
    var uname:String,
    var uemail:String,
    var password:String,
    var propic: String?= null,
    var tel:String,
    var age: Int?= null,
    var gender: String?= null,
    var country: String?= null,
    var added: String,
    var modified:String? = null,
    var deleted:Int?=null
): Serializable
