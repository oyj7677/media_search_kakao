package com.oyj.mediasearch.network

import okhttp3.Interceptor
import okhttp3.Response
import com.oyj.mediasearch.BuildConfig

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newBuilder = chain.request().newBuilder()
        newBuilder.addHeader(CONTENT_TYPE, CONTENT_TYPE_CONTENT).build()
        newBuilder.addHeader(AUTHORIZATION, "KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}").build()

        val request = newBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        private const val CONTENT_TYPE = "Content-Type"
        private const val CONTENT_TYPE_CONTENT = "application/json"
        private const val AUTHORIZATION = "Authorization"
    }
}
