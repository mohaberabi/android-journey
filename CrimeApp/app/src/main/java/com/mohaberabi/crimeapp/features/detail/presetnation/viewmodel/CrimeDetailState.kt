package com.mohaberabi.crimeapp.features.detail.presetnation.viewmodel

import java.util.Date

data class CrimeDetailState(
    val title: String = "",
    val isSolved: Boolean = false,
    val date: Date = Date(),
    val suspect: String = "",
    val choosedImgName: String? = null,
    val tempChoosedImge: String = "",
)
