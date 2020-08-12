package com.easyapps.ncraftmedia.dto

import com.easyapps.ncraftmedia.model.AttachmentModel
import com.easyapps.ncraftmedia.model.AttachmentType

class AttachmentResponseDto(
    val id: String,
    val type: AttachmentType
) { companion object {
    fun toModel(dto: AttachmentResponseDto) = AttachmentModel(
        dto.id,
        dto.type
    )
}

}