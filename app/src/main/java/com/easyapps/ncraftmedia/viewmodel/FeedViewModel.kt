package com.easyapps.ncraftmedia.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyapps.ncraftmedia.App
import com.easyapps.ncraftmedia.error.AuthException
import com.easyapps.ncraftmedia.model.PostModel
import com.easyapps.ncraftmedia.repository.PostRepository
import com.easyapps.ncraftmedia.service.PostService
import kotlinx.coroutines.launch
import java.nio.channels.UnresolvedAddressException
//TODO SocketTimeoutException
class FeedViewModel : ViewModel() {

    private val repo: PostRepository = App.postRepository
    private val postService = PostService()

    private val _posts: MutableLiveData<UiState> =
        MutableLiveData()
    val posts: LiveData<UiState>
        get() = _posts

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _posts.value = UiState.EmptyProgress

            tryGetAllPosts()
        }
    }

    fun updateData() {
        viewModelScope.launch {
            val curPosts = when (val posts = _posts.value) {
                is UiState.Success -> posts.posts
                else -> emptyList()
            }

            _posts.value = UiState.Update(curPosts)

            tryGetAllPosts()
        }
    }

    private suspend fun tryGetAllPosts() {
        _posts.value = try {
            UiState.Success(repo.getAll())
        } catch (e: UnresolvedAddressException) {
            UiState.InternetAccessError
        } catch (e: AuthException) {
            Log.d("MyTag", "AuthException in FeedViewModel")
            UiState.AuthError
        }
    }

    fun likeByMe(post: PostModel) {
        viewModelScope.launch {
            val likedByMe = !post.likedByMe

            val newPost = if (likedByMe) {
                post.copy(countLikes = post.countLikes.inc(), likedByMe = likedByMe)
                postService.likeById(post.id)
            } else {
                post.copy(countLikes = post.countLikes.dec(), likedByMe = likedByMe)
                postService.disLikeById(post.id)
            }

            updatePost(newPost)

            try {
                repo.save(newPost)
            } catch (e: UnresolvedAddressException) {
                updatePost(post)
            }
        }
    }

    fun dislikeByMe(post: PostModel) {
        viewModelScope.launch {

        }
    }

    private fun updatePost(post: PostModel) {
        when (val state = _posts.value) {
            is UiState.Success -> {
                _posts.value = state.copy(updateList(state.posts, post))
            }
            is UiState.Update -> {
                _posts.value = state.copy(updateList(state.posts, post))
            }
            else -> return
        }
    }

    private fun updateList(oldList: List<PostModel>, newPost: PostModel): List<PostModel> =
        oldList.toMutableList().apply {
            set(indexOfFirst { it.id == newPost.id }, newPost)
        }
}