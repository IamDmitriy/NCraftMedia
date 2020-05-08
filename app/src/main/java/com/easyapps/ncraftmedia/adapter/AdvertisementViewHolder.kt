package com.easyapps.ncraftmedia.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import com.easyapps.ncraftmedia.dto.Post
import kotlinx.android.synthetic.main.post_feed_advertisement_card.view.*

class AdvertisementViewHolder(postAdapter: PostAdapter, view: View) :
    BaseViewHolder(postAdapter, view) {
    override fun bind(post: Post) {
        with(itemView) {
            initView(post)

            btnFollow.setOnClickListener {
                if (post.link != null) {

                    val intent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse(post.link)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

}