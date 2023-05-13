package com.example.touristo.repository

import android.annotation.SuppressLint
import com.example.touristo.dao.AdminDao
import com.example.touristo.modal.Admin
import com.example.touristo.modal.LogTime
import com.example.touristo.modal.User
import kotlinx.coroutines.*

class AdminRepository(private val dao: AdminDao, private val ioDispatcher: CoroutineDispatcher) {
    private val adminRepositoryScope = CoroutineScope(ioDispatcher)
    fun getAllAdmin(): List<Admin> {
        return dao.getAllAdmin()
    }
    suspend fun insertAdmin(admin: Admin){
        adminRepositoryScope.launch {
            dao.insertAdmin(admin)
        }
    }
    suspend fun userExist(email: String): Int {
        var userExists = -1
        withContext(ioDispatcher) {
            userExists = dao.getUserExist(email)
        }
        return userExists
    }
    suspend fun getUserLogin(email:String,password:String):Int{
        var userExists = -1
        withContext(ioDispatcher) {
            userExists=dao.getUserLogin(email,password)
        }

        return userExists
    }

    suspend fun loginByCard(email:String):Int{
        var userExists = -1
        withContext(ioDispatcher) {
            userExists=dao.loginByCard(email)
        }

        return userExists
    }
    suspend fun insertLoggedTime(logTime: LogTime){
        withContext(ioDispatcher) {
            dao.insertLoggedTime(logTime)
        }
    }
    suspend fun getAdminByEmail(email: String):Admin{
        var adminObj: Admin
        withContext(ioDispatcher){
            adminObj =dao.getAllAdminByEmail(email)
        }
        return adminObj
    }
    fun deleteAdmin(admin: Admin){
        adminRepositoryScope.launch(Dispatchers.IO) {
            dao.deleteAdmin(admin)
        }
    }

    suspend fun updateAdminProfile(fname:String, lname:String, password:String, age:Int, tel: String, propic: String,
                                   gender:String, designation: String,modified:String,aemail:String):Int{
        var result = 0
        withContext(ioDispatcher) {
            result=dao.updateAdminProfile(fname,lname, password,age,tel, propic,gender, designation, modified, aemail)
        }
        return result
    }

    @SuppressLint("Range")
    fun getAdminLogs(email: String): Array<String>? {
        var adminInfo: Array<String>? = null
        val cursor = dao.getAdminLogs(email)
        if (cursor.moveToFirst() == true) {
            val logcount = cursor.getInt(cursor.getColumnIndex("logcount")).toString()
            val inquiry = cursor.getInt(cursor.getColumnIndex("inrep")).toString()
            adminInfo = arrayOf(logcount, inquiry)
        }
        cursor.close()
        return adminInfo
    }
    suspend fun deleteAdminByEmail(email: String):Int{
        var result: Int
        withContext(ioDispatcher){
            result =dao.deleteAdminByEmail(email)
        }
        return result
    }


}