package com.mohaberabi.crimeapp.features.detail.presetnation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.crimeapp.CrimeApplication
import com.mohaberabi.crimeapp.core.data.CrimeRepository
import com.mohaberabi.crimeapp.core.model.Crime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class CrimeDetailViewModel(
    private val repository: CrimeRepository
) : ViewModel() {


    private val _state = MutableStateFlow(CrimeDetailState())
    val state = _state.asStateFlow()

    fun chooseDate(date: Date) = _state.update { it.copy(date = date) }
    fun suspectChanged(suspect: String) = _state.update { it.copy(suspect = suspect) }
    fun tempImgChanged(imgName: String) = _state.update { it.copy(tempChoosedImge = imgName) }
    fun choosedImgChanged() = _state.update { it.copy(choosedImgName = it.tempChoosedImge) }

    fun toggleIsSolved() = _state.update { it.copy(isSolved = !it.isSolved) }
    fun titleChange(title: String) = _state.update { it.copy(title = title) }
    fun addCrime() {
        viewModelScope.launch {
            val crime = Crime(
                title = _state.value.title,
                isSolved = _state.value.isSolved,
                date = Date(),
                suspect = _state.value.suspect
            )
            repository.addCrime(crime)
        }
    }
}