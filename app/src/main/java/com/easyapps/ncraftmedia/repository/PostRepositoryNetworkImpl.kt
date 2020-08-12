package com.easyapps.ncraftmedia.repository

import android.graphics.Bitmap
import com.easyapps.ncraftmedia.api.*
import com.easyapps.ncraftmedia.dto.*
import com.easyapps.ncraftmedia.error.AuthException
import com.easyapps.ncraftmedia.error.PostNotFoundException
import com.easyapps.ncraftmedia.model.AttachmentModel
import com.easyapps.ncraftmedia.model.PostModel
import com.easyapps.ncraftmedia.model.User
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.IOException


const val baseUrl =
    "https://ncraftmedia.herokuapp.com"

class PostRepositoryNetworkImpl : PostRepository {
    private var retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
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
            .baseUrl(baseUrl)
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
            throw Exception() //TODO конкретизировать исключения
        }
    }

    override suspend fun deleteById(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(model: PostModel): PostModel {
        val response = api.savePost(
            PostRequestDto.fromModel(model)
        )

        if (response.isSuccessful) {
            return PostResponseDto.toModel(requireNotNull(response.body()))
        } else {
            throw Exception("Ошибка при создании поста")
        }
    }

    override suspend fun repost(repostedId: Long, content: String): PostModel {
        val repostRequestDto = RepostRequestDto(content)
        val response = api.repost(repostedId, repostRequestDto)
        val postResponseDto = requireNotNull(response.body())
        return PostResponseDto.toModel(postResponseDto)
    }

    override suspend fun getPostsCreatedBefore(
        idCurPost: Long,
        countUploadedPosts: Int
    ): List<PostModel> {
        val requestData = PostsCreatedBeforeRequestDto(idCurPost, countUploadedPosts)
        val response = api.getPostsCreatedBefore(requestData)
        val postResponseDtoList = requireNotNull(response.body())
        return postResponseDtoList.map(PostResponseDto.Companion::toModel)
    }

    override suspend fun getPostsAfter(idFirstPost: Long): List<PostModel> {
        val response = api.getPostsAfter(idFirstPost)
        val postResponseDtoList = requireNotNull(response.body())
        return postResponseDtoList.map(PostResponseDto.Companion::toModel)
    }

    override suspend fun getRecentPosts(countPosts: Int): List<PostModel> {
        val response = api.getRecentPosts(countPosts)
        val postResponseDtoList = requireNotNull(response.body())
        return postResponseDtoList.map(PostResponseDto.Companion::toModel)
    }

    override suspend fun upload(bitmap: Bitmap): AttachmentModel {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val reqFIle = RequestBody.create(
            MediaType.let { "image/jpeg".toMediaTypeOrNull() },
            bos.toByteArray()
        )
        val body = MultipartBody.Part.createFormData("file", "image.jpg", reqFIle)
        val response = api.uploadImage(body)
        return response.body()!!
    }

    override suspend fun createPost(content: String, attachment: AttachmentModel?): PostModel {
        val response = api.createPost(CreatePostRequestDto(content, attachment))
        return PostResponseDto.toModel(response.body()!!)
    }

    override fun getUrlByAttachmentId(attachmentId: String): String {
        return "$baseUrl/api/v1/static/$attachmentId"
    }
}
