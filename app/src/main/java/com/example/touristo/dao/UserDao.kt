package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.LogTime
import com.example.touristo.modal.User
import org.jetbrains.annotations.NotNull

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user:User)

    @Delete
    suspend fun deleteUser(user:User)

    @Query("SELECT * FROM user")
    fun getAllUsers():List<User>

    @Query("SELECT COUNT(uid) FROM user WHERE uemail=:email")
    fun getUserExist(email: String):Int

    @Query("SELECT COUNT(uid) FROM user WHERE uemail=:email AND password=:password")
    fun getUserLogin( email: String, password:String):Int

   @Insert
   suspend fun insertLoggedTime(logTime: LogTime)
}