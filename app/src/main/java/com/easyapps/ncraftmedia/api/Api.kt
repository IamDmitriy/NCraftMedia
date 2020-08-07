package com.easyapps.ncraftmedia.api

import com.easyapps.ncraftmedia.dto.PostResponseDto
import com.easyapps.ncraftmedia.dto.PostsCreatedBeforeRequestDto
import com.easyapps.ncraftmedia.dto.RepostRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("api/v1/posts/{id} ")
    suspend fun getPostById(@Path("id") id: Long): Response<PostResponseDto>

    @POST("/api/v1/posts")
    suspend fun createPost(@Body createPostRequest: PostResponseDto): Response<PostResponseDto>

    @POST("/api/v1/posts/{id}/reposts")
    suspend fun repost(@Path("id") id: Long, @Body repostRequestDto: RepostRequestDto)
            : Response<PostResponseDto>

    @POST("api/v1/posts/before")
    suspend fun getPostsCreatedBefore(@Body postsCreatedBeforeRequestDto: PostsCreatedBeforeRequestDto): Response<List<PostResponseDto>>

    @GET("api/v1/posts/{id}/after")
    suspend fun getPostsAfter(@Path("id") idFirstPost: Long): Response<List<PostResponseDto>>

    @GET("api/v1/posts/{count}/recent/")
    suspend fun getRecentPosts(@Path("count")countPosts: Int): Response<List<PostResponseDto>>
}