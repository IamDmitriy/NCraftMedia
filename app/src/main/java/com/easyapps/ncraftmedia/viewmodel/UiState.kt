package com.easyapps.ncraftmedia.viewmodel

import com.easyapps.ncraftmedia.model.PostModel

sealed class UiState {

    data class Update(val posts: List<PostModel>) : UiState()

    object EmptyProgress : UiState()

    object InternetAccessError : UiState()

    object AuthError : UiState()

    data class Success(val posts: List<PostModel>) : UiState()
}