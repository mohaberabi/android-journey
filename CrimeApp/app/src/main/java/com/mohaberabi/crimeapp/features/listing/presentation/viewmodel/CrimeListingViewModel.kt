package com.mohaberabi.crimeapp.features.listing.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.crimeapp.CrimeApplication
import com.mohaberabi.crimeapp.core.data.CrimeRepository
import com.mohaberabi.crimeapp.core.di.AppModule
import com.mohaberabi.crimeapp.core.model.Crime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import java.time.Instant
import java.util.Date
import java.util.UUID
import kotlin.random.Random

class CrimeListingViewModel(
    private val repository: CrimeRepository = CrimeApplication.appModule.crimeRepository
) : ViewModel() {

    val crimes = repository.getAllCrimes().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

}