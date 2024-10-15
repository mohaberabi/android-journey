package com.mohaberabi.crimeapp.core.database

import androidx.room.TypeConverter
import java.util.Date


class CrimeTypeConvertor {

    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toDate(time: Long) = Date(time)
}