package com.anderson.nutritionapp.di


import com.anderson.nutritionapp.util.Constants.BEARER_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $BEARER_TOKEN")
            .build()
        return chain.proceed(request)
    }
}
