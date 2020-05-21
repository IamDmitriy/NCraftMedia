package com.easyapps.ncraftmedia.adapter.viewholders

import android.content.Intent
import android.net.Uri
import android.view.View
import com.easyapps.ncraftmedia.adapter.PostAdapter
import com.easyapps.ncraftmedia.model.PostModel
import kotlinx.android.synthetic.main.post_card.view.*


class VideoViewHolder(postAdapter: PostAdapter, view: View) : BaseViewHolder(postAdapter, view) {
    override fun bind(post: PostModel) {
        with(itemView) {
            initView(post)

            imgvVideo.visibility = View.VISIBLE

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