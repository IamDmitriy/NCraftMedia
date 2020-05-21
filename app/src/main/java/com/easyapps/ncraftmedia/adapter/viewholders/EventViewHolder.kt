package com.easyapps.ncraftmedia.adapter.viewholders

import android.content.Intent
import android.net.Uri
import android.view.View
import com.easyapps.ncraftmedia.R
import com.easyapps.ncraftmedia.adapter.PostAdapter
import com.easyapps.ncraftmedia.model.PostModel
import kotlinx.android.synthetic.main.post_card.view.*

class EventViewHolder(postAdapter: PostAdapter, view: View) : BaseViewHolder(postAdapter, view) {
    override fun bind(post: PostModel) {
        with(itemView) {
            initView(post)
            val hasLocation: Boolean

            if (post.address != null || post.location != null) {
                hasLocation = true
                btnLocation.setImageResource(R.drawable.ic_location_on__24dp)
            } else {
                hasLocation = false
                btnLocation.setImageResource(R.drawable.ic_location_add__24dp)
            }

            btnLocation.visibility = View.VISIBLE

            btnLocation.setOnClickListener {
                if (hasLocation) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_VIEW

                        when {
                            post.address != null -> data = Uri.parse("geo:?q=${post.address}")
                            post.location != null -> data =
                                Uri.parse("geo:${post.location.lat},${post.location.lon}")
                        }
                    }

                    this.context.startActivity(intent)
                } //TODO реализовать обработчик для добавления координат
            }

        }
    }

}