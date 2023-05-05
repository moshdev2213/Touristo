package com.example.touristo.typeConverters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.sql.Timestamp

class TimestampConverter {
    @TypeConverter
    fun fromTimestamp(timestamp: Long): Timestamp {
        return Timestamp(timestamp)
    }

    @TypeConverter
    fun toTimestamp(value: Timestamp): Long {
        return value.time
    }

    //the bitmap converter CLass
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray? {
        if (bitmap == null) {
            return null
        }
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }


    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap? {
        if (byteArray == null) {
            return null
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }


}