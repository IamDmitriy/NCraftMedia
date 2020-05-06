package com.easyapps.ncraftmedia

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.easyapps.ncraftmedia.dto.Post
import kotlinx.android.synthetic.main.post_card.view.*
import java.text.SimpleDateFormat
import java.util.*

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(post: Post) {
        with(itemView) {
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
            }

            if (post.sharedByMe) {
                btnShare.setImageResource(R.drawable.ic_share_active_24dp)
                tvCountShares.setTextColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.activeText
                    )
                )
            }

            if (post.countLikes == 0) {
                tvCountLikes.visibility = View.INVISIBLE
            }

            if (post.countComments == 0) {
                tvCountComments.visibility = View.INVISIBLE
            }

            if (post.countShares == 0) {
                tvCountShares.visibility = View.INVISIBLE
            }

            tvCreated.text = publishedAgoInMillisToTimeInWords(
                System.currentTimeMillis() - post.created
            )

            if (post.videoUrl != "") {
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

            if (post.type == PostType.EVENT) {
                btnLocation.visibility = View.VISIBLE

                btnLocation.setOnClickListener {
                    val intent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = if (post.address != "") {
                            Uri.parse("geo:?q=${post.address}")
                        } else {
                            Uri.parse("geo:${post.location.lat},${post.location.lon}")
                        }
                    }

                    this.context.startActivity(intent)
                }
            } else {
                btnLocation.visibility = View.GONE
            }

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