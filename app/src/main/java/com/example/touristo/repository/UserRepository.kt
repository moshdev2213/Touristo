package com.example.touristo.repository

import androidx.lifecycle.LiveData
import com.example.touristo.dao.UserDao
import com.example.touristo.modal.User
import kotlinx.coroutines.*

class UserRepository(private val dao: UserDao,private val ioDispatcher: CoroutineDispatcher) {
    private val userRepositoryScope = CoroutineScope(ioDispatcher)

    fun getAllUsers(): List<User> {
        val users = dao.getAllUsers()
        return users
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
}