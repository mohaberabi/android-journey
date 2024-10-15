package com.mohaberabi.fliker.core.network

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class Photo(
    val id: String,
    val title: String,
    val secret: String,
    val server: String,
) {
    val url: String get() = "https://live.staticflickr.com/${server}/${id}_${secret}_w.jpg"
}

@Serializable
data class PhotosResponse(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: Int,
    val photo: List<Photo>
)

@Serializable
data class GalleryResponse(
    val photos: PhotosResponse
)
