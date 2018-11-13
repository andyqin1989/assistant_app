package com.assistant.uaua.framework.network

import com.assistant.uaua.framework.network.covert.ASGsonConverterFactory
import com.assistant.uaua.framework.network.interceptor.RequestInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

/** Created by qinbaoyuan on 2018/11/12
 */
open class ASNetManager private constructor() {
    init {
        createRetrofit()
    }

    private lateinit var retrofit: Retrofit

    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = ASNetManager()
    }

    private fun createRetrofit() {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(RequestInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("***")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ASGsonConverterFactory.create())
            .build()

    }

    public fun getRetrofit(): Retrofit {
        return retrofit
    }
}