package com.easyapps.ncraftmedia.adapter

import android.view.View
import com.easyapps.ncraftmedia.dto.Post

class PostViewHolder(postAdapter: PostAdapter, view: View) : BaseViewHolder(postAdapter, view) {
    override fun bind(post: Post) {
        initView(post)
    }


}