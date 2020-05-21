package com.easyapps.ncraftmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyapps.ncraftmedia.App
import com.easyapps.ncraftmedia.model.PostModel
import com.easyapps.ncraftmedia.repository.PostRepository
import kotlinx.coroutines.launch
import java.nio.channels.UnresolvedAddressException

class MainViewModel : ViewModel() {
    val STATUS_START_UPDATE: Int = 0
    val STATUS_END_UPDATE: Int = 1
    val STATUS_UNRESOLVED_ADDRESS_EXCEPTION: Int = 2

    private val repo: PostRepository = App.postRepository

    val status: MutableLiveData<Int> = MutableLiveData(-1)

    private val _posts: MutableLiveData<UiState> =
        MutableLiveData<UiState>()
    val posts: LiveData<UiState>
        get() = _posts

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _posts.value = UiState.EmptyProgress
            try {
                _posts.value = UiState.Success(repo.getAll())
            } catch (e: UnresolvedAddressException) {
                _posts.value = UiState.Error
            }
        }
    }

    fun updateData() {
        viewModelScope.launch {
            val curPosts = when (val posts = _posts.value) {
                is UiState.Success -> posts.posts
                else -> emptyList()
            }

            _posts.value = UiState.Update(curPosts)

            try {
                _posts.value = UiState.Success(repo.getAll())
            } catch (e: UnresolvedAddressException) {
                _posts.value = UiState.Error
            }
        }
    }

    fun likeByMe(post: PostModel) {
        viewModelScope.launch {
            val likedByMe = !post.likedByMe

            val newPost = if (likedByMe) {
                post.copy(countLikes = post.countLikes.inc(), likedByMe = likedByMe)
            } else {
                post.copy(countLikes = post.countLikes.dec(), likedByMe = likedByMe)
            }

            updatePost(newPost)

            try {
                repo.save(newPost)
            } catch (e: UnresolvedAddressException) {
                updatePost(post)
            }
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