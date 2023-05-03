package com.example.touristo.repository

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



}