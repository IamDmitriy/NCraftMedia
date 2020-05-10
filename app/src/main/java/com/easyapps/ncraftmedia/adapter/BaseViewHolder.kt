package com.easyapps.ncraftmedia.adapter

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.easyapps.ncraftmedia.R
import com.easyapps.ncraftmedia.dto.Post
import java.text.SimpleDateFormat
import java.util.*

const val LOG_TAG = "MyLog"

abstract class BaseViewHolder(val postAdapter: PostAdapter, view: View) :
    RecyclerView.ViewHolder(view) {


    abstract fun bind(post: Post)

    fun initView(post: Post) {
        with(itemView) {
            val tvContent = this.findViewById<TextView>(R.id.tvContent)
            val tvAuthor = this.findViewById<TextView>(R.id.tvAuthor)
            val tvCountLikes = this.findViewById<TextView>(R.id.tvCountLikes)
            val tvCountComments = this.findViewById<TextView>(R.id.tvCountComments)
            val tvCountShares = this.findViewById<TextView>(R.id.tvCountShares)
            val tvCreated = this.findViewById<TextView>(R.id.tvCreated)
            val btnComment = this.findViewById<ImageButton>(R.id.btnComment)
            val btnLike = this.findViewById<ImageButton>(R.id.btnLike)
            val btnShare = this.findViewById<ImageButton>(R.id.btnShare)
            val btnHide = this.findViewById<ImageButton>(R.id.btnHide)

            tvContent.text = post.content
            tvAuthor.text = post.author
            tvCountLikes.text = post.countLikes.toString()
            tvCountComments.text = post.countComments.toString()
            tvCountShares.text = post.countShares.toString()

            if (post.commentedByMe) {
                btnComment.setImageResource(R.drawable.ic_comment_active_24dp)
                tvCountComments.setTextColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.activeText
                    )
                )
            } else {
                btnComment.setImageResource(R.drawable.ic_comment_inactive_24dp)
                tvCountComments.setTextColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.regularText
                    )
                )
            }

            if (post.sharedByMe) {
                btnShare.setImageResource(R.drawable.ic_share_active_24dp)
                tvCountShares.setTextColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.activeText
                    )
                )
            } else {
                btnShare.setImageResource(R.drawable.ic_share_inactive_24dp)
                tvCountShares.setTextColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.regularText
                    )
                )
            }

            if (post.likedByMe) {
                btnLike.setImageResource(R.drawable.ic_favorite_active_24dp)
                tvCountLikes.setTextColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.activeText
                    )
                )
            } else {
                btnLike.setImageResource(R.drawable.ic_favorite_inactive_24dp)
                tvCountLikes.setTextColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.regularText
                    )
                )
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
                post.likedByMe = !post.likedByMe

                if (post.likedByMe) {
                    btnLike.setImageResource(R.drawable.ic_favorite_active_24dp)
                    tvCountLikes.setTextColor(
                        ContextCompat.getColor(
                            this.context,
                            R.color.activeText
                        )
                    )

                    post.countLikes++
                    tvCountLikes.text = post.countLikes.toString()
                } else {
                    btnLike.setImageResource(R.drawable.ic_favorite_inactive_24dp)
                    tvCountLikes.setTextColor(
                        ContextCompat.getColor(
                            this.context,
                            R.color.regularText
                        )
                    )

                    post.countLikes--
                    tvCountLikes.text = post.countLikes.toString()
                }

                if (post.countLikes == 0) {
                    tvCountLikes.visibility = View.INVISIBLE
                } else {
                    tvCountLikes.visibility = View.VISIBLE
                }
                postAdapter.notifyItemChanged(adapterPosition)
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
        }
    }

    private fun publishedAgoInMillisToTimeInWords(publishedAgo: Long): String =
        when (publishedAgo / 1000) {
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