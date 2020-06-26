package com.easyapps.ncraftmedia.api

import com.easyapps.ncraftmedia.dto.PostResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class AuthRequestParams(val username: String, val password: String)
data class RegistrationRequestParams(val username: String, val password: String)
data class Token(val token: String)

interface API {
    @POST("api/v1/authentication")
    suspend fun authenticate(@Body authRequestParams: AuthRequestParams): Response<Token>

    @POST("api/v1/registration")
    suspend fun register(@Body registrationRequestParams: RegistrationRequestParams): Response<Token>

    @GET("api/v1/posts")
    suspend fun getAllPosts(): Response<List<PostResponseDto>>

    @POST("/api/v1/posts")
    suspend fun createPost(@Body createPostRequest: PostResponseDto): Response<PostResponseDto>
}