package com.example.touristo.repository

import android.annotation.SuppressLint
import com.example.touristo.dao.UserDao
import com.example.touristo.modal.LogTime
import com.example.touristo.modal.User
import kotlinx.coroutines.*

class UserRepository(private val dao: UserDao,private val ioDispatcher: CoroutineDispatcher) {
    private val userRepositoryScope = CoroutineScope(ioDispatcher)

    fun getAllUsers(): List<User> {
        return dao.getAllUsers()
    }
    suspend fun userExist(email: String): Int {
        var userExists = -1
        withContext(ioDispatcher) {
            userExists = dao.getUserExist(email)
        }
        return userExists
    }
    fun insertUser(user:User){
        userRepositoryScope.launch(Dispatchers.IO) {
            dao.insertUser(user)
        }
    }
    fun updateUser(user:User){
        userRepositoryScope.launch(Dispatchers.IO) {
            dao.updateUser(user)
        }
    }
    fun deleteUser(user:User){
        userRepositoryScope.launch(Dispatchers.IO) {
            dao.deleteUser(user)
        }
    }

    suspend fun getuserLogin(email:String,password:String):Int{
        var userExists = -1
        withContext(ioDispatcher) {
            userExists=dao.getUserLogin(email,password)
        }

        return userExists
    }

    suspend fun insertLoggedTime(logTime: LogTime){
        withContext(ioDispatcher) {
            dao.insertLoggedTime(logTime)
        }
    }

    suspend fun getUserObject(email:String): User {
        var userObj:User
        withContext(ioDispatcher){
             userObj = dao.getUserObject(email)
        }
        return userObj
    }

    suspend fun updateUserProfile(country:String?, gender:String?, age:Int?, tel:String, propic: String?, password: String,
        uname:String, email: String):Int{
        var result = 0
        withContext(ioDispatcher) {
            result=dao.updateUserProfile(country,gender,age,tel,propic,password,uname,email)
        }
        println(country+""+gender)
        return result
    }

    suspend fun updateUserProfileAsAdmin(country:String?, gender:String?, age:Int?, tel:String, propic: String?, password: String,
                                  uname:String, email: String,deleted:Int):Int{
        var result = 0
        withContext(ioDispatcher) {
            result=dao.updateUserProfileAsAdmin(country,gender,age,tel,propic,password,uname,email,deleted)
        }
        println(country+""+gender)
        return result
    }

    suspend fun deleteUserAccount(email: String):Int{
        var result = 0
        withContext(ioDispatcher){
            result = dao.deleteUserAccount(email)
        }
        return result
    }

    @SuppressLint("Range")
    fun selectTouristManageProfile(email: String): Array<String>? {
        var adminInfo: Array<String>? = null
        val cursor = dao.selectTouristManageProfile(email)
        if (cursor?.moveToFirst() == true) {
            val amount = cursor.getString(cursor.getColumnIndex("amount"))?.let {
                String.format("%.2f", it.toDoubleOrNull() ?: 0.0)
            } ?: "0.00"
            val logcount = cursor.getInt(cursor.getColumnIndex("logcount")).toString()
            val inquiry = cursor.getInt(cursor.getColumnIndex("inquiry")).toString()
            val bookcount = cursor.getInt(cursor.getColumnIndex("bookcount")).toString()
            val lastbookdate = cursor.getString(cursor.getColumnIndex("lastbookdate")) ?: "No Booking"
            val villaName = cursor.getString(cursor.getColumnIndex("villaName")) ?: "No Booking"
            val lastPay = cursor.getString(cursor.getColumnIndex("lastPay")) ?: "No Payments"
            adminInfo = arrayOf(amount, logcount, inquiry, bookcount, lastbookdate, villaName, lastPay)
        }
        cursor?.close()
        return adminInfo
    }


}