package com.example.touristo.dao

import android.database.Cursor
import android.graphics.Bitmap
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

    @Query("UPDATE user SET propic=:propic WHERE uemail=:uemail;")
    fun updateImage(propic: String,uemail: String):Int

    @Query("UPDATE user SET country =:country, gender=:gender , age=:age , tel=:tel , password=:password ,uname=:uname  WHERE uemail =:uemail;")
    fun updateUserProfile(
        country:String?, gender:String?, age:Int?, tel:String, password: String,
        uname:String, uemail: String):Int

    @Query("UPDATE user SET country =:country, gender=:gender , age=:age , tel=:tel ,  password=:password ,uname=:uname,deleted=:deleted  WHERE uemail =:uemail;")
    fun updateUserProfileAsAdmin(
        country:String?, gender:String?, age:Int?, tel:String,  password: String,
        uname:String, uemail: String,deleted:Int):Int

    @Query("SELECT \n" +
            "    SUM(v.price) as amount, -- total amount paid for all bookings\n" +
            "    (\n" +
            "        SELECT COUNT(lg.id) \n" +
            "        FROM logtime lg\n" +
            "        WHERE lg.email =:email AND lg.role = \"user\"\n" +
            "    ) as logcount, -- number of log entries for the user\n" +
            "    (\n" +
            "        SELECT COUNT(uq.id)\n" +
            "        FROM userInquiry uq\n" +
            "        WHERE uq.uemail =:email \n" +
            "    ) as inquiry, -- number of user inquiries made by the user\n" +
            "    (\n" +
            "        SELECT COUNT(b.id)\n" +
            "        FROM booking b\n" +
            "        WHERE b.uemail =:email\n" +
            "    ) as bookcount, -- number of bookings made by the user\n" +
            "    (\n" +
            "        SELECT  strftime('%Y.%d.%m', b.added) \n" +
            "        FROM booking b\n" +
            "        WHERE b.uemail =:email\n" +
            "        ORDER BY b.id DESC\n" +
            "        LIMIT 1\n" +
            "    ) as lastbookdate, -- date of last booking made by the user\n" +
            "    (\n" +
            "        SELECT b.bookedVilaName\n" +
            "        FROM  booking b\n" +
            "        WHERE b.uemail =:email\n" +
            "        ORDER BY b.id DESC\n" +
            "        LIMIT 1\n" +
            "    ) as villaName, -- name of the last villa booked by the user\n" +
            "    (\n" +
            "        SELECT  strftime('%Y.%d.%m', p.added)\n" +
            "        FROM payment p\n" +
            "        WHERE p.uemail =:email\n" +
            "        ORDER BY p.id DESC\n" +
            "        LIMIT 1\n" +
            "    ) as lastPay -- date and time of the last payment made by the user, with AM/PM indicator\n" +
            "FROM payment p, booking b, villa v\n" +
            "WHERE p.uemail = b.uemail AND b.villaId = v.id AND p.uemail =:email\n")
    fun selectTouristManageProfile(email:String):Cursor?
}