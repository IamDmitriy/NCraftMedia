package com.easyapps.ncraftmedia.dto

import com.easyapps.ncraftmedia.model.Location
import com.easyapps.ncraftmedia.model.PostModel
import com.easyapps.ncraftmedia.model.PostType

class PostResponseDto(
    val id: Long,
    val author: String,
    val content: String,
    val created: Long,
    val likedByMe: Boolean = false,
    val countLikes: Int = 10,
    val commentedByMe: Boolean = false,
    val countComments: Int = 0,
    val sharedByMe: Boolean = false,
    val countShares: Int = 0,
    val videoUrl: String? = null,
    val type: PostType = PostType.POST,
    val source: PostModel? = null,
    val address: String? = null,
    val location: Location? = null,
    val link: String? = null
) {

    companion object {
        fun fromModel(model: PostModel) = PostResponseDto(
            id = model.id,
            author = model.author,
            content = model.content,
            created = model.created,
            likedByMe = model.likedByMe,
            countLikes = model.countLikes,
            commentedByMe = model.commentedByMe,
            countComments = model.countComments,
            sharedByMe = model.sharedByMe,
            countShares = model.countShares,
            videoUrl = model.videoUrl,
            type = model.type,
            source = model.source,
            address = model.address,
            location = model.location,
            link = model.link
        )

        fun toModel(dto: PostResponseDto) = PostModel(
            id = dto.id,
            author = dto.author,
            content = dto.content,
            created = dto.created,
            likedByMe = dto.likedByMe,
            countLikes = dto.countLikes,
            commentedByMe = dto.commentedByMe,
            countComments = dto.countComments,
            sharedByMe = dto.sharedByMe,
            countShares = dto.countShares,
            videoUrl = dto.videoUrl,
            type = dto.type,
            source = dto.source,
            address = dto.address,
            location = dto.location,
            link = dto.link
        )
    }
}
