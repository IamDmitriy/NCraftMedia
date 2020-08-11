package com.easyapps.ncraftmedia.repository

import android.graphics.Bitmap
import com.easyapps.ncraftmedia.api.Token
import com.easyapps.ncraftmedia.model.AttachmentModel
import com.easyapps.ncraftmedia.model.PostModel
import com.easyapps.ncraftmedia.model.User
import retrofit2.Response

interface PostRepository {
    suspend fun getAll(): List<PostModel>
    suspend fun getById(id: Long): PostModel?
    suspend fun save(item: PostModel): PostModel
    suspend fun deleteById(id: Long)
    fun createRetrofitWithAuth(token: String, userAuth: User)
    suspend fun authenticate(login: String, password: String): Token
    suspend fun register(login: String, password: String): Response<Token>
    suspend fun getUserAuth(): User
    suspend fun repost(repostedId: Long, content: String): PostModel
    suspend fun getPostsCreatedBefore(dateCreationPost: Long, countPosts: Int): List<PostModel>
    suspend fun getPostsAfter(idFirstPost: Long): List<PostModel>
    suspend fun getRecentPosts(countPosts: Int): List<PostModel>
    suspend fun upload(bitmap: Bitmap): AttachmentModel
}