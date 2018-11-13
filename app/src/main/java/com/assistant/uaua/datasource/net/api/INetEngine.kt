package com.assistant.uaua.datasource.net.api

import com.assistant.uaua.datasource.net.request.GetUserByIdRequest
import com.assistant.uaua.datasource.net.request.LoginRequest
import com.assistant.uaua.datasource.net.request.RegisterRequest
import com.assistant.uaua.datasource.net.response.AllUserListResponse
import com.assistant.uaua.datasource.net.response.LoginResponse
import com.assistant.uaua.datasource.net.response.RegisterResponse
import com.assistant.uaua.datasource.net.response.LFBaseResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/** Created by qinbaoyuan on 2018/11/12
 */
interface INetEngine {
    @POST("userService/api/getUser")
    fun getAllUser(@Body request: GetUserByIdRequest): Observable<LFBaseResponse>

    @GET("userService/api/getAllUser")
    fun getUserList(): Observable<AllUserListResponse>

    @POST("userService/account/register")
    fun register(@Body quest: RegisterRequest): Observable<RegisterResponse>

    @POST("userService/account/login")
    fun login(@Body request: LoginRequest): Observable<LoginResponse>
}