package com.mohaberabi.fliker.features.gallery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.fliker.core.data.FlickerRepository
import com.mohaberabi.fliker.core.network.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class GalleryViewModel(
    private val flickerRepository: FlickerRepository
) : ViewModel() {


    private val _photos = MutableStateFlow<List<Photo>>(listOf())
    val photos = _photos.asStateFlow()

    init {
        getPhotos()
    }

    private fun getPhotos() {
        viewModelScope.launch {
            flickerRepository.getPhotos().onSuccess { response ->
                _photos.update {
                    response.photos.photo
                }
            }
        }
    }
}