package com.mohaberabi.fliker.core.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mohaberabi.fliker.core.data.FlickerRepository
import com.mohaberabi.fliker.core.network.ApiConst
import com.mohaberabi.fliker.core.network.FlickerService
import com.mohaberabi.fliker.core.network.PhotoInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppModule {
    val flickerRepository: FlickerRepository
}


class DefaultAppModule : AppModule {
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val contentType = "application/json".toMediaType()
    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(PhotoInterceptor())
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }
    private val retroFit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConst.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
    private val flickerService by lazy {
        retroFit.create(FlickerService::class.java)
    }
    override val flickerRepository: FlickerRepository by lazy {
        FlickerRepository(flickerService)
    }
}