package com.easyapps.ncraftmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.easyapps.ncraftmedia.R
import com.easyapps.ncraftmedia.adapter.viewholders.*
import com.easyapps.ncraftmedia.model.PostModel
import com.easyapps.ncraftmedia.model.PostType
import com.easyapps.ncraftmedia.viewmodel.FeedViewModel

const val VIEW_TYPE_POST = 1
const val VIEW_TYPE_EVENT = 2
const val VIEW_TYPE_REPOST = 3
const val VIEW_TYPE_VIDEO = 4
const val VIEW_TYPE_ADVERTISEMENT = 5
const val VIEW_TYPE_ITEM_FOOTER = 6

fun viewTypeToPostType(viewType: Int): PostType = when (viewType) {
    VIEW_TYPE_POST -> PostType.POST
    VIEW_TYPE_EVENT -> PostType.EVENT
    VIEW_TYPE_REPOST -> PostType.REPOST
    VIEW_TYPE_VIDEO -> PostType.VIDEO
    VIEW_TYPE_ADVERTISEMENT -> PostType.ADVERTISEMENT
    else -> TODO("unknown view type")
}

class PostAdapter(val viewModel: FeedViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var repostsBtnClickListener: RepostsBtnClickListener? = null
    var postList: List<PostModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var footerViewHolder: FooterViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if (viewType == VIEW_TYPE_ITEM_FOOTER) {
                footerViewHolder = FooterViewHolder(this, LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_load_more, parent, false))

                return footerViewHolder as FooterViewHolder
            }

            return when (viewTypeToPostType(viewType)) {
                PostType.POST -> PostViewHolder(
                    this, LayoutInflater.from(parent.context)
                        .inflate(R.layout.post_card, parent, false)
                )
                PostType.EVENT -> EventViewHolder(
                    this, LayoutInflater.from(parent.context)
                        .inflate(R.layout.post_card, parent, false)
                )
                PostType.REPOST -> RepostViewHolder(
                    this, LayoutInflater.from(parent.context)
                        .inflate(R.layout.post_card, parent, false)
                )
                PostType.VIDEO -> VideoViewHolder(
                    this, LayoutInflater.from(parent.context)
                        .inflate(R.layout.post_card, parent, false)
                )
                PostType.ADVERTISEMENT -> AdvertisementViewHolder(
                    this, LayoutInflater.from(parent.context)
                        .inflate(R.layout.post_card, parent, false)
                )
            }
    }

    override fun getItemCount(): Int {
        val footer = 1
        return postList.size + footer
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == postList.size) return

        with(holder as BaseViewHolder) {
            holder.bind(postList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == postList.size) return VIEW_TYPE_ITEM_FOOTER

        return when (postList[position].type) {
            PostType.POST -> VIEW_TYPE_POST
            PostType.EVENT -> VIEW_TYPE_EVENT
            PostType.REPOST -> VIEW_TYPE_REPOST
            PostType.VIDEO -> VIEW_TYPE_VIDEO
            PostType.ADVERTISEMENT -> VIEW_TYPE_ADVERTISEMENT
        }
    }

    fun setSuccessfullStateFooter() {
        footerViewHolder?.setSuccessfullState()
    }

    interface RepostsBtnClickListener {
        fun onRepostsBtnClicked(item: PostModel)
    }


}

