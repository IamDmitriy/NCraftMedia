package com.easyapps.ncraftmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.easyapps.ncraftmedia.adapter.PostAdapter
import com.easyapps.ncraftmedia.dto.Post
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postList: List<Post> = generateContent()

        with(rvContainer) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PostAdapter().apply {
                this.postList = postList
            }
        }

    }

    private fun generateContent(): List<Post> {

        return listOf(
            Post(
                id = 1,
                author = "Netology",
                content = "First post in our network!",
                created = 1588712400000,
                type = PostType.EVENT,
                address = "Варшавское ш., 1, с. 17. Бизнес-центр W Plaza-2",
                sharedByMe = true,
                countShares = 1,
                commentedByMe = true,
                countComments = 1
            ), Post(
                id = 2,
                author = "Netology",
                content = "First post in our network!",
                created = 1583010000000,
                type = PostType.EVENT,
                location = Location(55.703810, 37.623851)
            ), Post(
                id = 3,
                author = "Netology",
                content = "REPOST: First post in our network!",
                created = 1583010000000,
                source = Post(
                    id = 10,
                    author = "Netology",
                    content = "someContent",
                    created = 1551819600000
                ),
                type = PostType.REPOST
            ), Post(
                id = 3,
                author = "Netology",
                content = "REPOST: First post in our network!",
                created = 1583010000000,
                type = PostType.REPOST
            ), Post(
                id = 4,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                type = PostType.ADVERTISEMENT,
                link = "https://www.google.com/"
            ), Post(
                id = 5,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                videoUrl = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
                type = PostType.VIDEO
            ), Post(
                id = 6,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                type = PostType.POST
            ), Post(
                id = 1,
                author = "Netology",
                content = "First post in our network!",
                created = 1588712400000,
                type = PostType.EVENT,
                address = "Варшавское ш., 1, с. 17. Бизнес-центр W Plaza-2"
            ), Post(
                id = 2,
                author = "Netology",
                content = "First post in our network!",
                created = 1583010000000,
                type = PostType.EVENT,
                location = Location(55.703810, 37.623851)
            ), Post(
                id = 3,
                author = "Netology",
                content = "REPOST: First post in our network!",
                created = 1583010000000,
                source = Post(
                    id = 10,
                    author = "Netology",
                    content = "someContent",
                    created = 1551819600000
                ),
                type = PostType.REPOST
            ), Post(
                id = 4,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                type = PostType.ADVERTISEMENT,
                link = "https://www.google.com/"
            ), Post(
                id = 5,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                videoUrl = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
                type = PostType.VIDEO
            ), Post(
                id = 6,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                type = PostType.POST
            ), Post(
                id = 1,
                author = "Netology",
                content = "First post in our network!",
                created = 1588712400000,
                type = PostType.EVENT,
                address = "Варшавское ш., 1, с. 17. Бизнес-центр W Plaza-2"
            ), Post(
                id = 2,
                author = "Netology",
                content = "First post in our network!",
                created = 1583010000000,
                type = PostType.EVENT,
                location = Location(55.703810, 37.623851)
            ), Post(
                id = 3,
                author = "Netology",
                content = "REPOST: First post in our network!",
                created = 1583010000000,
                source = Post(
                    id = 10,
                    author = "Netology",
                    content = "someContent",
                    created = 1551819600000
                ),
                type = PostType.REPOST
            ), Post(
                id = 4,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                type = PostType.ADVERTISEMENT,
                link = "https://www.google.com/"
            ), Post(
                id = 5,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                videoUrl = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
                type = PostType.VIDEO
            ), Post(
                id = 6,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                type = PostType.POST
            )
        )
    }
}
