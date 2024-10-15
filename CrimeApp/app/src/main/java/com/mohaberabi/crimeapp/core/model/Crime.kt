package com.mohaberabi.crimeapp.core.model

import java.util.Date
import java.util.UUID

data class Crime(


    val id: Long? = null,
    val title: String,
    val date: Date,
    val isSolved: Boolean,
    val suspect: String = "",
    val photoName: String? = null,
)
