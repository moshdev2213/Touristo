package com.example.touristo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.touristo.modal.UserInquery

@Dao
interface InquiryDao {

    //in here the both type of the inquiries are included
    //1.the inquiry
    //2.the inquiry Reply


    //the userInquiry starts here
    @Insert
    suspend fun insertInquiry(userInquery: UserInquery)

    @Update
    suspend fun updateInquiry(userInquery: UserInquery)

    @Delete
    suspend fun deleteInquiry(userInquery: UserInquery)

    @Query("SELECT * FROM inquiryReply")
    fun getAllUsers(): LiveData<List<UserInquery>>

    //the InquiryReply starts here

}