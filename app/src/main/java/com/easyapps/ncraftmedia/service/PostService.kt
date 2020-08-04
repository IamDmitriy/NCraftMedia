package com.easyapps.ncraftmedia.service

import com.easyapps.ncraftmedia.App
import com.easyapps.ncraftmedia.model.PostModel

class PostService {
    private val repo = App.postRepository

    suspend fun likeById(id: Long): PostModel {
        val oldPost = repo.getById(id)!!
        val newPost = oldPost.copy(
            countLikes = oldPost.countLikes.inc(),
            likedByMe = true, //TODO добавляем авторство пользователя и проверку лайкал ли автор
            author = repo.getUserAuth().login
        )
        return repo.save(newPost)
    }

    suspend fun dislikeById(id: Long): PostModel {
        val oldPost = repo.getById(id)!!
        val newPost = oldPost.copy(
            countLikes = oldPost.countLikes.dec(),
            likedByMe = false,
            author = repo.getUserAuth().login
        )
        return repo.save(newPost)
    }
}