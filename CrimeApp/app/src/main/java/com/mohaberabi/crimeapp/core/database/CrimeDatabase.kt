package com.mohaberabi.crimeapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [CrimeEntity::class],
    version = 1,
)
@TypeConverters(CrimeTypeConvertor::class)
abstract class CrimeDatabase : RoomDatabase() {

    abstract fun crimeDao(): CrimeDao

}

