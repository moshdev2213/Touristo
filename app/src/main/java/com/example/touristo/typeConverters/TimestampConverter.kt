package com.example.touristo.typeConverters

import androidx.room.TypeConverter
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
}