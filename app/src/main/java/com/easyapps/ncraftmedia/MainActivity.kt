package com.easyapps.ncraftmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
            created = "20 august 2019"
        )

        txtCreated.text = post.created
        txtContent.text = post.content
        txtCountLikes.text = post.countLikes.toString()
        txtCountComments.text = post.countComments.toString()
        txtCountShares.text = post.countShares.toString()

        if (post.likedByMe) {
            btnLike.setImageResource(R.drawable.ic_favorite_active_24dp)
            txtCountLikes.setTextColor(resources.getColor(R.color.activeText))
        }

        if (post.commentedByMe) {
            btnComment.setImageResource(R.drawable.ic_comment_active_24dp)
            txtCountComments.setTextColor(resources.getColor(R.color.activeText))
        }

        if (post.sharedByMe) {
            btnShare.setImageResource(R.drawable.ic_share_active_24dp)
            txtCountShares.setTextColor(resources.getColor(R.color.activeText))
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

    }
}
