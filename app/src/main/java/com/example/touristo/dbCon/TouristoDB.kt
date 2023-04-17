package com.example.touristo.dbCon

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.touristo.dao.*
import com.example.touristo.modal.*
import com.example.touristo.typeConverters.TimestampConverter

@Database(entities = [
    Admin::class,
    User::class,
    Booking::class,
    Favourite::class,
    InqueryReply::class,
    Payment::class,
    Salary::class,
    UserInquery::class,
    Villa::class,
], version = 1, exportSchema = false)
@TypeConverters(TimestampConverter::class)
abstract class TouristoDB :RoomDatabase(){
    //dao getting
    abstract fun adminDao():AdminDao
    abstract fun userDao():UserDao
    abstract fun bookingDao():BookingDao
    abstract fun favouriteDao():FavouriteDao
    abstract fun inquiryDao():InquiryDao
    abstract fun paymentDao():PaymentDao
    abstract fun salaryDao():SalaryDao
    abstract fun villaDao():VillaDao

    companion object{
        @Volatile
        private var INSTANCE:TouristoDB?=null
        fun getInstance(context:Context):TouristoDB{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        TouristoDB::class.java,
                        "touristoDB"
                    ).build()
                }
                return instance
            }
        }
    }

}