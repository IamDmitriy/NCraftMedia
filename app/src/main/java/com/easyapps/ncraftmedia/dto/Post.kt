package com.easyapps.ncraftmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val created: Long,
    var likedByMe: Boolean = false,
    var countLikes: Int = 0,
    var commentedByMe: Boolean = false,
    var countComments: Int = 0,
    var sharedByMe: Boolean = false,
    var countShares: Int = 0
)