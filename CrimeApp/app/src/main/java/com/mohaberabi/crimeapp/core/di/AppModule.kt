package com.mohaberabi.crimeapp.core.di

import android.content.Context
import androidx.room.Room
import com.mohaberabi.crimeapp.core.data.CrimeRepository
import com.mohaberabi.crimeapp.core.database.CrimeDatabase

interface AppModule {
    abstract val crimeDatabase: CrimeDatabase
    abstract val crimeRepository: CrimeRepository
}


class DefaultAppModule(
    private val context: Context
) : AppModule {
    override val crimeDatabase: CrimeDatabase
            by lazy {
                Room.databaseBuilder(
                    context,
                    CrimeDatabase::class.java,
                    "crime.db"
                ).build()
            }

    override val crimeRepository: CrimeRepository
            by lazy {
                CrimeRepository(crimeDatabase)
            }
}