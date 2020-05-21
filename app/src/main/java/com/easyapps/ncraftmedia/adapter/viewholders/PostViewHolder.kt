package com.easyapps.ncraftmedia.adapter.viewholders

import android.view.View
import com.easyapps.ncraftmedia.adapter.PostAdapter
import com.easyapps.ncraftmedia.model.PostModel

class PostViewHolder(postAdapter: PostAdapter, view: View) : BaseViewHolder(postAdapter, view) {
    override fun bind(post: PostModel) {
        initView(post)
    }


}