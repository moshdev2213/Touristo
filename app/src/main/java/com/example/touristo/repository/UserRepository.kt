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

}