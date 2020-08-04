package com.easyapps.ncraftmedia.repository

import com.easyapps.ncraftmedia.error.AuthException
import com.easyapps.ncraftmedia.api.InjectAuthTokenInterceptor
import com.easyapps.ncraftmedia.api.API
import com.easyapps.ncraftmedia.api.AuthRequestParams
import com.easyapps.ncraftmedia.api.RegistrationRequestParams
import com.easyapps.ncraftmedia.api.Token
import com.easyapps.ncraftmedia.dto.PostRequestDto
import com.easyapps.ncraftmedia.dto.PostResponseDto
import com.easyapps.ncraftmedia.error.PostNotFoundException
import com.easyapps.ncraftmedia.model.PostModel
import com.easyapps.ncraftmedia.model.User
import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


const val serverUrl =
    "https://ncraftmedia.herokuapp.com/api/v1/posts"

class PostRepositoryNetworkImpl : PostRepository {
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }

    private var retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl("https://ncraftmedia.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private var api: API =
        retrofit.create(
            API::class.java
        )

    private lateinit var userAuth: User

    override suspend fun getUserAuth(): User = userAuth

    override suspend fun authenticate(login: String, password: String): Token {
        val response = api.authenticate(
            AuthRequestParams(login, password)
        )

        val token = requireNotNull(response.body())
        userAuth = User(login)

        if (response.isSuccessful) {
            createRetrofitWithAuth(
                token.token,
                userAuth
            )
        } else {
            if (response.code() == 401) {
                throw AuthException()
            }
        }

        return token
    }

    override suspend fun register(login: String, password: String) =
        api.register(
            RegistrationRequestParams(login, password)
        )

    override fun createRetrofitWithAuth(token: String, userAuth: User) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(InjectAuthTokenInterceptor(authToken = token))
            .addInterceptor(httpLoggingInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://ncraftmedia.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(API::class.java)

        this.userAuth = userAuth
    }

    override suspend fun getAll(): List<PostModel> {
        val response = api.getAllPosts()

        if (response.isSuccessful) {
            val postResponseDtoList: List<PostResponseDto> = requireNotNull(response.body())
            return postResponseDtoList.map(PostResponseDto.Companion::toModel)
        } else throw IOException()
    }

    override suspend fun getById(id: Long): PostModel {
        val response = api.getPostById(id)

        if (response.isSuccessful) {
            val postResponseDto = response.body() ?: throw PostNotFoundException()
            return PostResponseDto.toModel(postResponseDto)
        } else {
            throw Exception()
        }
    }

    override suspend fun deleteById(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(model: PostModel): PostModel {
        val response = api.createPost(
            PostRequestDto.fromModel(model)
        )

        if (response.isSuccessful) {
            return PostResponseDto.toModel(requireNotNull(response.body()))
        } else {
            throw Exception("Ошибка при создании поста")
        }
    }
}