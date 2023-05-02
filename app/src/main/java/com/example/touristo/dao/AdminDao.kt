package com.example.touristo.dao

import android.database.Cursor
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
    fun getAllAdmin(): LiveData<List<Admin>>

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


}