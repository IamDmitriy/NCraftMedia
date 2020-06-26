package com.easyapps.ncraftmedia.adapter.viewholders

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.easyapps.ncraftmedia.R
import com.easyapps.ncraftmedia.adapter.PostAdapter
import com.easyapps.ncraftmedia.model.PostModel
import java.text.SimpleDateFormat
import java.util.*

const val LOG_TAG = "MyLog"

abstract class BaseViewHolder(val postAdapter: PostAdapter, view: View) :
    RecyclerView.ViewHolder(view) {
    private val tvContent = itemView.findViewById<TextView>(R.id.tvContent)!!
    private val tvAuthor = itemView.findViewById<TextView>(R.id.tvAuthor)
    private val tvCountLikes = itemView.findViewById<TextView>(R.id.tvCountLikes)
    private val tvCountComments = itemView.findViewById<TextView>(R.id.tvCountComments)
    private val tvCountShares = itemView.findViewById<TextView>(R.id.tvCountShares)
    private val tvCreated = itemView.findViewById<TextView>(R.id.tvCreated)
    private val btnComment = itemView.findViewById<ImageButton>(R.id.btnComment)
    private val btnLike = itemView.findViewById<ImageButton>(R.id.btnLike)
    private val btnShare = itemView.findViewById<ImageButton>(R.id.btnShare)
    private val btnHide = itemView.findViewById<ImageButton>(R.id.btnHide)
    private val btnRepost = itemView.findViewById<ImageButton>(R.id.btnRepost)

    abstract fun bind(post: PostModel)

    fun initView(post: PostModel) {
        with(itemView) {
            tvContent.text = post.content
            tvAuthor.text = post.author
            tvCountLikes.text = post.countLikes.toString()
            tvCountComments.text = post.countComments.toString()
            tvCountShares.text = post.countShares.toString()

            //TODO реализовать проверку авторства из списка для какого либо действия
            // и установку флагов someActionByMe
            val commentedByMe = post.commentedByMe
            val repostedByMe = post.sharedByMe
            val likedByMe = post.likedByMe

            if (commentedByMe) {
                setActiveCommentedByMe()
            } else {
                setInactiveCommentedByMe()
            }
            //TODO изменить на repostedByMe
            if (repostedByMe) {
                setActiveRepostedByMe()
            } else {
                setInactiveRepostedByMe()
            }

            if (likedByMe) {
                setActiveLikedByMe()
            } else {
                setInactiveLikedByMe()
            }

            if (post.countLikes == 0) {
                tvCountLikes.visibility = View.INVISIBLE
            } else {
                tvCountLikes.visibility = View.VISIBLE
            }

            if (post.countComments == 0) {
                tvCountComments.visibility = View.INVISIBLE
            } else {
                tvCountComments.visibility = View.VISIBLE
            }

            if (post.countShares == 0) {
                tvCountShares.visibility = View.INVISIBLE
            } else {
                tvCountShares.visibility = View.VISIBLE
            }

            tvCreated.text = publishedAgoInMillisToTimeInWords(
                System.currentTimeMillis() - post.created
            )

            btnLike.setOnClickListener {
                if (likedByMe) {
                    postAdapter.viewModel.likeByMe(post)
                } else {
                    postAdapter.viewModel.dislikeByMe(post)
                }

            }

            btnShare.setOnClickListener {
                val formattedCreationDate: String = SimpleDateFormat(
                    "dd.MM.yyyy HH:mm",
                    Locale("ru")
                ).format(Date(post.created))

                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT, """
                        ${post.author} ($formattedCreationDate)
                        
                        ${post.content}
                    """.trimIndent()
                    )
                    type = "text/plain"
                }

                this.context.startActivity(intent)
            }

            btnHide.setOnClickListener {
                Log.d(LOG_TAG, "Скрываем пост с id = ${post.id}")
                postAdapter.postList = postAdapter.postList.filter {
                    it.id != post.id
                }
            }

            btnRepost.setOnClickListener {
                TODO()
            }
        }
    }

    private fun setInactiveLikedByMe() {
        btnLike.setImageResource(R.drawable.ic_favorite_inactive_24dp)
        tvCountLikes.setTextColor(
            ContextCompat.getColor(
                itemView.context,
                R.color.regularText
            )
        )
    }

    private fun setActiveLikedByMe() {
        btnLike.setImageResource(R.drawable.ic_favorite_active_24dp)
        tvCountLikes.setTextColor(
            ContextCompat.getColor(
                itemView.context,
                R.color.activeText
            )
        )
    }

    private fun setInactiveRepostedByMe() {
        btnRepost.setImageResource(R.drawable.ic_repost_inactive_24dp)
        tvCountShares.setTextColor(
            ContextCompat.getColor(
                itemView.context,
                R.color.regularText
            )
        )
    }

    private fun setActiveRepostedByMe() {
        btnRepost.setImageResource(R.drawable.ic_repost_active_24dp)
        tvCountShares.setTextColor(
            ContextCompat.getColor(
                itemView.context,
                R.color.activeText
            )
        )
    }

    private fun setInactiveCommentedByMe() {
        btnComment.setImageResource(R.drawable.ic_comment_inactive_24dp)
        tvCountComments.setTextColor(
            ContextCompat.getColor(
                itemView.context,
                R.color.regularText
            )
        )
    }

    private fun setActiveCommentedByMe() {
        btnComment.setImageResource(R.drawable.ic_comment_active_24dp)
        tvCountComments.setTextColor(
            ContextCompat.getColor(
                itemView.context,
                R.color.activeText
            )
        )
    }

    private fun publishedAgoInMillisToTimeInWords(publishedAgo: Long): String {
        return when (publishedAgo / 1000) {
            in 0..30 -> "менее минуты назад"
            in 31..90 -> "минуту назад"
            in 91..360 -> "6 минут назад"
            in 361..3600 -> "час назад"
            in 3601..7200 -> "2 часа назад"
            in 7201..86_400 -> "несколько часов назад"
            in 86_401..172_800 -> "день назад"
            in 172_801..604_800 -> "несколько дней назад"
            in 604_801..1_209_600 -> "неделю назад"
            in 1_209_601..2_678_400 -> "несколько недель назад"
            in 2_678_401..5_356_800 -> "месяц назад"
            in 5_356_801..32_140_800 -> "несколько месяцев назад"
            in 32_140_801..64_281_600 -> "год назад"
            else -> "несколько лет назад"
        }
    }

}