package com.easyapps.ncraftmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.easyapps.ncraftmedia.PostType
import com.easyapps.ncraftmedia.R
import com.easyapps.ncraftmedia.dto.Post
import kotlinx.android.synthetic.main.post_feed_repost_card.view.*

class RepostViewHolder(postAdapter: PostAdapter, view: View) :
    BaseViewHolder(postAdapter, view) {
    override fun bind(post: Post) {
        with(itemView) {
            initView(post)
            if (post.source == null) {
                repostContainer.visibility = View.GONE
                return
            } else {
                repostContainer.visibility = View.VISIBLE
            }

            val repostedViewHolder = when (post.source.type) {
                PostType.POST -> PostViewHolder(
                    postAdapter, LayoutInflater.from(context)
                        .inflate(R.layout.post_feed_post_card, repostContainer, false)
                )
                PostType.EVENT -> EventViewHolder(
                    postAdapter, LayoutInflater.from(context)
                        .inflate(R.layout.post_feed_event_card, repostContainer, false)
                )
                PostType.REPOST -> RepostViewHolder(
                    postAdapter, LayoutInflater.from(context)
                        .inflate(R.layout.post_feed_repost_card, repostContainer, false)
                )
                PostType.VIDEO -> VideoViewHolder(
                    postAdapter, LayoutInflater.from(context)
                        .inflate(R.layout.post_feed_video_card, repostContainer, false)
                )
                PostType.ADVERTISEMENT -> AdvertisementViewHolder(
                    postAdapter, LayoutInflater.from(context)
                        .inflate(R.layout.post_feed_advertisement_card, repostContainer, false)
                )
            }

            repostedViewHolder.bind(post.source)

            repostContainer.addView(repostedViewHolder.itemView)

            repostedViewHolder.itemView.findViewById<ImageButton>(R.id.btnLike).visibility =
                View.GONE
            repostedViewHolder.itemView.findViewById<ImageButton>(R.id.btnShare).visibility =
                View.GONE
            repostedViewHolder.itemView.findViewById<ImageButton>(R.id.btnComment).visibility =
                View.GONE
            repostedViewHolder.itemView.findViewById<TextView>(R.id.tvCountLikes).visibility =
                View.GONE
            repostedViewHolder.itemView.findViewById<TextView>(R.id.tvCountComments).visibility =
                View.GONE
            repostedViewHolder.itemView.findViewById<TextView>(R.id.tvCountShares).visibility =
                View.GONE

        }
    }

}