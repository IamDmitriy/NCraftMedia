package com.easyapps.ncraftmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val created: Long,
    val likedByMe: Boolean = false,
    val countLikes: Int = 0,
    val commentedByMe: Boolean = false,
    val countComments: Int = 0,
    val sharedByMe: Boolean = false,
    val countShares: Int = 0
)