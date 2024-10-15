package com.mohaberabi.fliker.core.network

import okhttp3.Interceptor
import okhttp3.Response

class PhotoInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val newUrl = original.url.newBuilder()
            .addQueryParameter("api_key", ApiConst.API_KEY)
            .addQueryParameter("format", "json")
            .addQueryParameter("nojsoncallback", "1")
            .addQueryParameter("safesearch", "1")
            .build()
        val newRequest = original.newBuilder().url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}