package com.mohaberabi.crimeapp.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohaberabi.crimeapp.core.model.Crime
import java.util.Date


@Entity("crime")
data class CrimeEntity(


    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val title: String,
    val date: Date,
    val isSolved: Boolean,
)


fun CrimeEntity.toCrime() = Crime(
    id = id,
    title = title,
    date = date,
    isSolved = isSolved,
)

fun Crime.toCrimeEntity() = CrimeEntity(
    id = id,
    title = title,
    date = date,
    isSolved = isSolved,
)
