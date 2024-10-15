package com.mohaberabi.fliker.core.data

import com.mohaberabi.fliker.core.network.FlickerService
import com.mohaberabi.fliker.core.network.GalleryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FlickerRepository(
    private val api: FlickerService
) {


    suspend fun getPhotos(): Result<GalleryResponse> {
        return try {
            withContext(Dispatchers.IO) {
                val photos = api.getPhotos()
                Result.success(photos)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}