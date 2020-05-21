package com.easyapps.ncraftmedia.adapter.viewholders

import android.content.Intent
import android.net.Uri
import android.view.View
import com.easyapps.ncraftmedia.adapter.PostAdapter
import com.easyapps.ncraftmedia.model.PostModel
import kotlinx.android.synthetic.main.post_card.view.*

class AdvertisementViewHolder(postAdapter: PostAdapter, view: View) :
    BaseViewHolder(postAdapter, view) {
    override fun bind(post: PostModel) {
        with(itemView) {
            initView(post)

            tvAdvertisement.visibility = View.VISIBLE
            tvCreated.visibility = View.GONE

            btnFollow.visibility = View.VISIBLE
            if (post.link == null) {
                //TODO добавить обработчик на добавление ссылки
                btnFollow.isEnabled = false
            } else {
                btnFollow.setOnClickListener {
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