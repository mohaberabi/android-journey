package com.mohaberabi.fliker.core.network

import retrofit2.http.GET

interface FlickerService {


    @GET(ApiConst.IMG_END_POINT)
    suspend fun getPhotos(): GalleryResponse
}