package com.example.touristo.viewModalProvider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.touristo.dao.UserDao
import com.example.touristo.viewModal.UserViewModal

class UserViewModalFactory(
    private val dao:UserDao
) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModal::class.java)){
            return UserViewModal(dao) as T
        }
        throw java.lang.IllegalArgumentException("Unknown View Model Class")
    }
}