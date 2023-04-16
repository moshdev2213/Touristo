package com.example.touristo.viewModal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.touristo.dao.UserDao
import com.example.touristo.modal.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModal(private val dao:UserDao):ViewModel() {
    val users = dao.getAllUsers()

    fun insertUser(user:User)=viewModelScope.launch {
        dao.insertUser(user)
    }

    fun updateUser(user:User)=viewModelScope.launch {
        dao.updateUser(user)
    }

    fun deleteUser(user:User)=viewModelScope.launch {
        dao.deleteUser(user)
    }

//    fun getUserExist(email: String)=viewModelScope.launch  {
//        val user = dao.getUserExist(email)
//    }
}