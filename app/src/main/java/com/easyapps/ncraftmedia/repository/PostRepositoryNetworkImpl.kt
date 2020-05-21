package com.easyapps.ncraftmedia.repository

import com.easyapps.ncraftmedia.dto.PostRequestDto
import com.easyapps.ncraftmedia.dto.PostResponseDto
import com.easyapps.ncraftmedia.model.PostModel
import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType


const val serverUrl =
    "https://ncraftmedia.herokuapp.com/api/v1/posts"

class PostRepositoryNetworkImpl : PostRepository {
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }

    override suspend fun getAll(): List<PostModel> {
        val postResponseDtoList: List<PostResponseDto> = client.get(serverUrl)
        return postResponseDtoList.map(PostResponseDto.Companion::toModel)
    }

    override suspend fun getById(id: Long): PostModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun save(item: PostModel): PostModel {
        val postResponseDto = client.post<PostResponseDto> {
            url(serverUrl)
            contentType(ContentType.Application.Json)
            body = PostRequestDto.fromModel(item)
        }
        return PostResponseDto.toModel(postResponseDto)
    }

    override suspend fun removeById(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun likeById(id: Long): PostModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun dislikeById(id: Long): PostModel? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}