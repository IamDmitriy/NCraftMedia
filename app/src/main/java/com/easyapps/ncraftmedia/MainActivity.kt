package com.easyapps.ncraftmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.easyapps.ncraftmedia.adapter.PostAdapter
import com.easyapps.ncraftmedia.dto.Post
import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.http.ContentType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val url =
    "https://raw.githubusercontent.com/IamDmitriy/HW6.1.1.DataLoading/master/posts.json"

class MainActivity : AppCompatActivity() {
    private val client = HttpClient {
        install(JsonFeature) {
            acceptContentTypes = listOf(
                ContentType.Text.Plain,
                ContentType.Application.Json
            )
            serializer = GsonSerializer()
        }
    }

    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postAdapter = PostAdapter()

        with(rvContainer) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postAdapter
        }

        CoroutineScope(Main + job).launch {
            postAdapter.postList = withContext(IO + job) {
                client.get<List<Post>>(url)
            }
        }
    }

}
