package com.example.touristo.dao

import android.database.Cursor
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.Admin
import com.example.touristo.modal.LogTime
import com.example.touristo.modal.User
@Dao
interface AdminDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdmin(admin: Admin)

    @Update
    suspend fun updateAdmin(admin: Admin)

    @Delete
    suspend fun deleteAdmin(admin: Admin)

    @Query("SELECT * FROM admin")
    fun getAllAdmin(): List<Admin>

    @Query("SELECT COUNT(aid) FROM admin WHERE aemail=:email ")
    fun getUserExist(email: String):Int

    @Query("SELECT COUNT(aid) FROM admin WHERE aemail=:email AND password=:password")
    fun getUserLogin( email: String, password:String):Int

    @Insert
    suspend fun insertLoggedTime(logTime: LogTime)

    @Query("SELECT a.aid, a.fname, COUNT(a.aid) AS adminCount, (\n" +
            "    SELECT strftime('%Y-%m-%d %H:%M:%S', l.log)\n" +
            "    FROM logtime l\n" +
            "    WHERE l.email=:email  AND l.role = \"admin\"\n" +
            "    ORDER BY l.id DESC\n" +
            "    LIMIT 1 OFFSET 1\n" +
            ") AS secondLastPunch, (SELECT COUNT(id) FROM userInquiry) as inquiry ,  (SELECT COUNT(id) FROM booking) as booking ,  (SELECT COUNT(uid) FROM user) as user\n" +
            "FROM admin a\n" +
            "WHERE a.aemail =  :email\n" +
            "GROUP BY a.aid, a.fname\n")
    fun getAdminNameAndCount(email: String):Cursor?

    @Query ("SELECT * FROM admin WHERE aemail=:email LIMIT 1")
    fun getAllAdminByEmail(email: String):Admin

    @Query("SELECT COUNT(ir.id) as inrep,(\n" +
            "           SELECT COUNT(lg.id) \n" +
            "           FROM logtime lg \n" +
            "           WHERE lg.role=\"admin\" AND lg.email=:email\n" +
            "           ) as logcount\n" +
            "FROM inquiryReply ir \n" +
            "WHERE ir.uemail=:email")
    fun getAdminLogs(email:String):Cursor

    @Query("UPDATE admin SET fname =:fname,lname=:lname, password=:password , age=:age , tel=:tel , propic=:propic , gender=:gender ,designation=:designation,modified=:modified  WHERE aemail =:email;")
    fun updateAdminProfile(
        fname:String, lname:String, password:String, age:Int, tel: String, propic: String,
        gender:String, designation: String,modified:String,email:String):Int

    @Query("DELETE FROM admin WHERE aemail=:email")
    fun deleteAdminByEmail(email:String):Int
}