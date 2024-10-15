package com.mohaberabi.crimeapp.core.data

import com.mohaberabi.crimeapp.core.database.CrimeDatabase
import com.mohaberabi.crimeapp.core.database.toCrime
import com.mohaberabi.crimeapp.core.database.toCrimeEntity
import com.mohaberabi.crimeapp.core.model.Crime
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CrimeRepository(
    private val crimeDatabase: CrimeDatabase,
) {


    suspend fun addCrime(crime: Crime): Result<Unit> {
        return try {
            crimeDatabase.crimeDao().addCrime(crime.toCrimeEntity())
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }


    fun getAllCrimes(): Flow<List<Crime>> =
        crimeDatabase.crimeDao().getAllCrimes().map { list ->
            list.map { it.toCrime() }
        }

}