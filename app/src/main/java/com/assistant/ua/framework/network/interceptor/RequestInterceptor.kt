package com.assistant.ua.framework.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/** Created by qinbaoyuan on 2018/11/12
 */
class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .build()
        return chain.proceed(request)
    }
}