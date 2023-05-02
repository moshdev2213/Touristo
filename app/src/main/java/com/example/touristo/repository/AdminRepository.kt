package com.example.touristo.repository

import com.example.touristo.dao.AdminDao
import com.example.touristo.modal.Admin
import com.example.touristo.modal.LogTime
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminRepository(private val dao: AdminDao, private val ioDispatcher: CoroutineDispatcher) {
    private val adminRepositoryScope = CoroutineScope(ioDispatcher)

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

}