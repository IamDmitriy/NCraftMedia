package com.easyapps.ncraftmedia.adapter.viewholders

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.easyapps.ncraftmedia.R
import com.easyapps.ncraftmedia.adapter.PostAdapter
import com.easyapps.ncraftmedia.model.PostModel
import com.easyapps.ncraftmedia.model.PostType
import kotlinx.android.synthetic.main.post_card.view.*


class RepostViewHolder(postAdapter: PostAdapter, view: View) :
    BaseViewHolder(postAdapter, view) {
    override fun bind(post: PostModel) {
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
                        .inflate(R.layout.post_card, repostContainer, false)
                )
                PostType.EVENT -> EventViewHolder(
                    postAdapter, LayoutInflater.from(context)
                        .inflate(R.layout.post_card, repostContainer, false)
                )
                PostType.REPOST -> RepostViewHolder(
                    postAdapter, LayoutInflater.from(context)
                        .inflate(R.layout.post_card, repostContainer, false)
                )
                PostType.VIDEO -> VideoViewHolder(
                    postAdapter, LayoutInflater.from(context)
                        .inflate(R.layout.post_card, repostContainer, false)
                )
                PostType.ADVERTISEMENT -> AdvertisementViewHolder(
                    postAdapter, LayoutInflater.from(context)
                        .inflate(R.layout.post_card, repostContainer, false)
                )
            }

            repostContainer.removeAllViews()

            repostedViewHolder.bind(post.source)

            repostContainer.addView(repostedViewHolder.itemView)

            repostedViewHolder.itemView.findViewById<ConstraintLayout>(R.id.bottomBar).visibility =
                View.GONE

            repostedViewHolder.itemView.findViewById<ImageButton>(R.id.btnHide).visibility =
                View.GONE
            repostedViewHolder.itemView.findViewById<ImageButton>(R.id.btnShare).visibility =
                View.GONE

        }
    }

}