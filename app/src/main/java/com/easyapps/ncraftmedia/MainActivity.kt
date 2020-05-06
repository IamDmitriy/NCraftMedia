package com.easyapps.ncraftmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
        val post1 = Post(
            id = 1,
            author = "Netology",
            content = "First post in our network!",
            created = 1588712400000,
            videoUrl = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
            type = PostType.EVENT,
            address = "Варшавское ш., 1, с. 17. Бизнес-центр W Plaza-2"//,
            //location = Location(55.703810, 37.623851)
        )

        val post2 = Post(
            id = 2,
            author = "Netology",
            content = "First post in our network!",
            created = 1583010000000,
            videoUrl = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
            type = PostType.EVENT,
            location = Location(55.703810, 37.623851)
        )

        val post3 = Post(
            id = 1,
            author = "Netology",
            content = "First post in our network!",
            created = 1551819600000,
            videoUrl = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
            type = PostType.BASE
        )

        val post4 = Post(
            id = 1,
            author = "Netology",
            content = "First post in our network!",
            created = 1520283600000,
            videoUrl = "https://www.youtube.com/watch?v=WhWc3b3KhnY",
            type = PostType.EVENT,
            address = "Варшавское ш., 1, с. 17. Бизнес-центр W Plaza-2"//,
            //location = Location(55.703810, 37.623851)
        )
        return listOf(post1, post2, post3, post4)
    }
}
