package com.easyapps.ncraftmedia.dto

import com.easyapps.ncraftmedia.model.AttachmentModel
import com.easyapps.ncraftmedia.model.Location
import com.easyapps.ncraftmedia.model.PostModel
import com.easyapps.ncraftmedia.model.PostType

class PostResponseDto(
    val id: Long,
    val author: String,
    val content: String,
    val created: Long,
    val likedByMe: Boolean,
    val countLikes: Int,
    val commentedByMe: Boolean,
    val countComments: Int,
    val sharedByMe: Boolean,
    val countShares: Int,
    val videoUrl: String?,
    val type: PostType,
    val source: PostModel?,
    val address: String?,
    val location: Location?,
    val link: String?,
    val attachment: AttachmentModel?
) {

    companion object {
        fun toModel(dto: PostResponseDto) = PostModel(
            dto.id,
            dto.author,
            dto.content,
            dto.created,
            dto.likedByMe,
            dto.countLikes,
            dto.commentedByMe,
            dto.countComments,
            dto.sharedByMe,
            dto.countShares,
            dto.videoUrl,
            dto.type,
            dto.source,
            dto.address,
            dto.location,
            dto.link,
            dto.attachment
        )
    }
}
