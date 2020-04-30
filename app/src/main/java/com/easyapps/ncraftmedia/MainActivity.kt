package com.easyapps.ncraftmedia

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.easyapps.ncraftmedia.dto.Post
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val post = Post(
            id = 1,
            author = "Netology",
            content = "First post in our network!",
            created = 1566302400
        )

        txtContent.text = post.content
        txtAuthor.text = post.author
        txtCountLikes.text = post.countLikes.toString()
        txtCountComments.text = post.countComments.toString()
        txtCountShares.text = post.countShares.toString()

        if (post.commentedByMe) {
            btnComment.setImageResource(R.drawable.ic_comment_active_24dp)
            txtCountComments.setTextColor(ContextCompat.getColor(this, R.color.activeText))
        }

        if (post.sharedByMe) {
            btnShare.setImageResource(R.drawable.ic_share_active_24dp)
            txtCountShares.setTextColor(ContextCompat.getColor(this, R.color.activeText))
        }

        if (post.countLikes == 0) {
            txtCountLikes.visibility = View.INVISIBLE
        }

        if (post.countComments == 0) {
            txtCountComments.visibility = View.INVISIBLE
        }

        if (post.countShares == 0) {
            txtCountShares.visibility = View.INVISIBLE
        }

        txtCreated.text = publishedAgoInSecondsToTimeInWords(
            System.currentTimeMillis() / 1000 - post.created
        )

        btnLike.setOnClickListener {
            post.likedByMe = !post.likedByMe

            if (post.likedByMe) {
                btnLike.setImageResource(R.drawable.ic_favorite_active_24dp)
                txtCountLikes.setTextColor(ContextCompat.getColor(this, R.color.activeText))

                post.countLikes++
                txtCountLikes.text = post.countLikes.toString()
            } else {
                btnLike.setImageResource(R.drawable.ic_favorite_inactive_24dp)
                txtCountLikes.setTextColor(ContextCompat.getColor(this, R.color.regularText))

                post.countLikes--
                txtCountLikes.text = post.countLikes.toString()
            }

            if (post.countLikes == 0) {
                txtCountLikes.visibility = View.INVISIBLE
            } else {
                txtCountLikes.visibility = View.VISIBLE
            }

        }

    }

    private fun publishedAgoInSecondsToTimeInWords(publishedAgo: Long): String =
        when (publishedAgo) {
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
