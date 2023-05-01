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

    @Query("SELECT COUNT(uid) FROM user WHERE uemail=:email AND password=:password AND deleted=0")
    fun getUserLogin( email: String, password:String):Int

   @Insert
   suspend fun insertLoggedTime(logTime: LogTime)

    @Query("SELECT * FROM user WHERE uemail=:email  AND deleted=0 LIMIT 1")
    fun getUserObject( email: String):User

    @Query("UPDATE user SET deleted=1 WHERE uemail=:uemail;")
    fun deleteUserAccount(uemail: String):Int

    @Query("UPDATE user SET country =:country, gender=:gender , age=:age , tel=:tel , propic=:propic , password=:password ,uname=:uname  WHERE uemail =:uemail;")
    fun updateUserProfile(
        country:String?, gender:String?, age:Int?, tel:String, propic: kotlin.String?, password: String,
        uname:String, uemail: String):Int
}