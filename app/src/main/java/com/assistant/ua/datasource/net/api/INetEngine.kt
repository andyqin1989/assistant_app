package com.assistant.ua.datasource.net.api

import com.assistant.ua.datasource.entity.BlogEntity
import com.assistant.ua.datasource.net.request.*
import com.assistant.ua.datasource.net.response.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/** Created by qinbaoyuan on 2018/11/12
 */
interface INetEngine {
    @POST("userService/app/getUser")
    fun getAllUser(@Body request: GetUserByIdRequest): Observable<LFBaseResponse>

    @GET("userService/app/getAllUser")
    fun getUserList(): Observable<AllUserListResponse>

    @POST("userService/app/register")
    fun register(@Body quest: RegisterRequest): Observable<RegisterResponse>

    @POST("userService/app/login")
    fun login(@Body request: LoginRequest): Observable<LoginResponse>

    @POST("userService/app/addBlog")
    fun addBlog(@Body request: AddBlogRequest): Observable<AddTaskResponse>

    @POST("userService/app/getAllBlog")
    fun getAllBlog(@Body request: GetAllBlogRequest): Observable<HttpResult<List<BlogEntity>>>
}