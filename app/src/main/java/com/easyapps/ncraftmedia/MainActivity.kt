package com.easyapps.ncraftmedia

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.easyapps.ncraftmedia.adapter.LOG_TAG
import com.easyapps.ncraftmedia.adapter.PostAdapter
import com.easyapps.ncraftmedia.dto.Post
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postList: List<Post> = generateContent()

        postList.forEach {
            Log.d(LOG_TAG, "${it.id}")
        }

        with(rvContainer) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PostAdapter().apply {
                this.postList = postList
            }
        }

    }

    private fun generateContent(): List<Post> {
        var maxId: Long = 0

        return listOf(
            Post(
                id = maxId++,
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
                id = maxId++,
                author = "Netology",
                content = "First post in our network!",
                created = 1583010000000,
                type = PostType.EVENT,
                location = Location(55.703810, 37.623851)
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "REPOST: First post in our network!",
                created = 1583010000000,
                source = Post(
                    id = maxId++,
                    author = "Netology",
                    content = "someContent",
                    created = 1551819600000
                ),
                type = PostType.REPOST
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "REPOST: First post in our network!",
                created = 1583010000000,
                type = PostType.REPOST
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                type = PostType.ADVERTISEMENT,
                link = "https://www.google.com/"
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                videoUrl = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
                type = PostType.VIDEO
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                type = PostType.POST
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "First post in our network!",
                created = 1588712400000,
                type = PostType.EVENT,
                address = "Варшавское ш., 1, с. 17. Бизнес-центр W Plaza-2"
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "First post in our network!",
                created = 1583010000000,
                type = PostType.EVENT,
                location = Location(55.703810, 37.623851)
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "REPOST: First post in our network!",
                created = 1583010000000,
                source = Post(
                    id = maxId++,
                    author = "Netology",
                    content = "someContent",
                    created = 1551819600000
                ),
                type = PostType.REPOST
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                type = PostType.ADVERTISEMENT,
                link = "https://www.google.com/"
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                videoUrl = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
                type = PostType.VIDEO
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                type = PostType.POST
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "First post in our network!",
                created = 1588712400000,
                type = PostType.EVENT,
                address = "Варшавское ш., 1, с. 17. Бизнес-центр W Plaza-2"
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "First post in our network!",
                created = 1583010000000,
                type = PostType.EVENT,
                location = Location(55.703810, 37.623851)
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "REPOST: First post in our network!",
                created = 1583010000000,
                source = Post(
                    id = maxId++,
                    author = "Netology",
                    content = "someContent",
                    created = 1551819600000
                ),
                type = PostType.REPOST
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                type = PostType.ADVERTISEMENT,
                link = "https://www.google.com/"
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                videoUrl = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
                type = PostType.VIDEO
            ), Post(
                id = maxId++,
                author = "Netology",
                content = "First post in our network!",
                created = 1520283600000,
                type = PostType.POST
            )
        )
    }
}
