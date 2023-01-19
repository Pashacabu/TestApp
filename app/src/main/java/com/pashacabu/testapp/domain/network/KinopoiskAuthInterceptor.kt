package com.pashacabu.testapp.domain.network

import okhttp3.Interceptor
import okhttp3.Response

class KinopoiskAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val url = request.url.newBuilder()
            .addQueryParameter(name = "token", TOKEN)
            .build()
        request = request.newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val TOKEN = "ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06"
    }
}