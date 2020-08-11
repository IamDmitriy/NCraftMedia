package com.easyapps.ncraftmedia.dto

import com.easyapps.ncraftmedia.model.AttachmentModel

data class CreatePostRequestDto(val content: String, val attachment: AttachmentModel?)
