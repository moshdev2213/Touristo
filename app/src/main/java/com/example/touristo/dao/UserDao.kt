package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user:User)

    @Delete
    suspend fun deleteUser(user:User)

    @Query("SELECT * FROM user")
    fun getAllUsers():LiveData<List<User>>

    @Query("SELECT COUNT(uid) FROM user WHERE uemail=:email")
    fun getUserExist(email: String):Int


}