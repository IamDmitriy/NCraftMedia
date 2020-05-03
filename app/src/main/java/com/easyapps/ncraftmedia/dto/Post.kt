package com.easyapps.ncraftmedia.dto

import com.easyapps.ncraftmedia.Location
import com.easyapps.ncraftmedia.PostType

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val created: Long,
    var likedByMe: Boolean = false,
    var countLikes: Int = 10,
    val commentedByMe: Boolean = false,
    val countComments: Int = 0,
    val sharedByMe: Boolean = false,
    val countShares: Int = 0,
    val videoUrl: String = "",
    val type: PostType = PostType.BASE,
    val address: String = "",
    val location: Location = Location(0.0, 0.0)
)