package com.easyapps.ncraftmedia.service

import com.easyapps.ncraftmedia.App
import com.easyapps.ncraftmedia.model.PostModel

class PostService {
    private val repo = App.postRepository

    suspend fun likeById(id: Long): PostModel {
        val oldPost = repo.getById(id)!!
        val newPost = oldPost.copy(
            countLikes = oldPost.countLikes.inc(),
            likedByMe = true,
            author = repo.getUserAuth().login
        )
        return repo.save(newPost)
    }

    suspend fun disLikeById(id: Long): PostModel {
        val oldPost = repo.getById(id)!!
        val newPost = oldPost.copy(
            countLikes = oldPost.countLikes.inc(),
            likedByMe = true,
            author = repo.getUserAuth().login
        )
        return repo.save(newPost)
    }
}