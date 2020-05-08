package com.easyapps.ncraftmedia.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import com.easyapps.ncraftmedia.dto.Post
import kotlinx.android.synthetic.main.post_feed_video_card.view.*

class VideoViewHolder(postAdapter: PostAdapter, view: View) : BaseViewHolder(postAdapter, view) {
    override fun bind(post: Post) {
        with(itemView) {
            initView(post)

            if (post.videoUrl != null) {
                imgvVideo.setOnClickListener {
                    val intent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse(post.videoUrl)
                    }

                    this.context.startActivity(intent)
                }
            } else {
                imgvVideo.visibility = View.GONE
            }
        }
    }

}