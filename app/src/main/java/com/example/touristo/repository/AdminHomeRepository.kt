package com.example.touristo.repository

import android.annotation.SuppressLint
import com.example.touristo.dao.AdminDao
import com.example.touristo.dao.BookingDao
import com.example.touristo.dao.PaymentDao
import com.example.touristo.dao.UserDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

class AdminHomeRepository(
    private val adminDao: AdminDao,
    private val paymentDao: PaymentDao,
    private val bookingDao: BookingDao,
    private val userDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher
    ) {
    private val adminHomeRepositoryScope = CoroutineScope(ioDispatcher)

    @SuppressLint("Range")
    fun getAdminInfo(email: String): Array<String>? {
        var adminInfo: Array<String>? = null
        val cursor = adminDao.getAdminNameAndCount(email)
        if (cursor?.moveToFirst() == true) {
            val fname = cursor.getString(cursor.getColumnIndex("fname"))
            val adminCount = cursor.getInt(cursor.getColumnIndex("adminCount"))
            val lastPunch = cursor.getString(cursor.getColumnIndex("secondLastPunch"))
            val inquiry = cursor.getString(cursor.getColumnIndex("inquiry"))
            val booking = cursor.getString(cursor.getColumnIndex("booking"))
            val user = cursor.getString(cursor.getColumnIndex("user"))
            adminInfo = arrayOf(fname, adminCount.toString(), lastPunch,inquiry,booking,user)
        }
        cursor?.close()
        return adminInfo
    }

//ends here


}