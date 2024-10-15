package com.mohaberabi.crimeapp.core.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao

interface CrimeDao {


    @Upsert
    suspend fun addCrime(crimeEntity: CrimeEntity)

    @Query("SELECT * FROM crime")
    fun getAllCrimes(): Flow<List<CrimeEntity>>
}