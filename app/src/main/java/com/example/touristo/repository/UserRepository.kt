package com.example.touristo.repository

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
        println("In repo: $userExists")
        return userExists
    }
    fun insertUser(user:User){
        GlobalScope.launch(Dispatchers.IO) {
            dao.insertUser(user)
        }
    }
    fun updateUser(user:User){
        GlobalScope.launch(Dispatchers.IO) {
            dao.updateUser(user)
        }
    }
    fun deleteUser(user:User){
        GlobalScope.launch(Dispatchers.IO) {
            dao.deleteUser(user)
        }
    }

    suspend fun getuserLogin(email:String,password:String):Int{
        var userExists = -1
        withContext(ioDispatcher) {
            userExists=dao.getUserLogin(email,password)
        }
        println("In getuserrepo: $userExists")
        return userExists
    }

    suspend fun insertLoggedTime(logTime: LogTime){
        withContext(ioDispatcher) {
            dao.insertLoggedTime(logTime)
        }
    }

}