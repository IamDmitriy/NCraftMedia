package com.easyapps.ncraftmedia.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.easyapps.ncraftmedia.IS_STARTED_WITH_AUTH_ERROR_KEY
import com.easyapps.ncraftmedia.R
import com.easyapps.ncraftmedia.REPOSTED_ID_KEY
import com.easyapps.ncraftmedia.adapter.PostAdapter
import com.easyapps.ncraftmedia.adapter.PostDiffUtilCallback
import com.easyapps.ncraftmedia.model.PostModel
import com.easyapps.ncraftmedia.viewmodel.FeedViewModel
import com.easyapps.ncraftmedia.viewmodel.UiState
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var postAdapter: PostAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        postAdapter = PostAdapter(viewModel)
        postAdapter.repostsBtnClickListener = object: PostAdapter.RepostsBtnClickListener {
            override fun onRepostsBtnClicked(item: PostModel) {
                goToRepostActivity(item.id)
            }
        }

        with(rvContainer) {
            layoutManager = LinearLayoutManager(this@FeedActivity)
            adapter = postAdapter
        }

        viewModel.posts.observe(this) { state ->
            when (state) {
                is UiState.Update -> {
                    tvErrorMessage.isVisible = false
                    btnRepeat.isVisible = false
                    progressBar.isVisible = false
                    swipeRefreshLayout.isRefreshing = true
                    rvContainer.isVisible = true

                    setOrUpdateAdapterBy(state.posts)

                }

                is UiState.EmptyProgress -> {
                    tvErrorMessage.isVisible = false
                    btnRepeat.isVisible = false
                    progressBar.isVisible = true
                    swipeRefreshLayout.isRefreshing = false
                    rvContainer.isVisible = false
                }

                is UiState.InternetAccessError -> {
                    tvErrorMessage.isVisible = true
                    tvErrorMessage.text = getString(R.string.message_no_internet_connection)
                    btnRepeat.isVisible = true
                    progressBar.isVisible = false
                    swipeRefreshLayout.isRefreshing = false
                    rvContainer.isVisible = false
                }

                is UiState.AuthError -> {
                    Log.d("MyTag", "AuthError in FeedActivity")
                    goToAuth()
                }

                is UiState.Success -> {
                    tvErrorMessage.isVisible = false
                    btnRepeat.isVisible = false
                    progressBar.isVisible = false
                    swipeRefreshLayout.isRefreshing = false
                    rvContainer.isVisible = true

                    setOrUpdateAdapterBy(state.posts)
                }
            }
        }

        swipeRefreshLayout.setOnRefreshListener(viewModel::updateData)
        btnRepeat.setOnClickListener {
            viewModel.loadData()
        }
        fab.setOnClickListener {
            startActivity(Intent(this, CreatePostActivity::class.java))
        }
    }

    private fun goToAuth() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.putExtra(IS_STARTED_WITH_AUTH_ERROR_KEY, true)
        startActivity(intent)
        finish()
    }

    private fun goToRepostActivity(repostedId: Long) {
        val intent = Intent(this, RepostActivity::class.java)
        intent.putExtra(REPOSTED_ID_KEY, repostedId)
        startActivity(intent)
    }

    private fun setOrUpdateAdapterBy(newList: List<PostModel>) {
        val postDiffUtilCallback =
            PostDiffUtilCallback(postAdapter.postList, newList)
        val postDiffResult = DiffUtil.calculateDiff(postDiffUtilCallback)
        postAdapter.postList = newList
        postDiffResult.dispatchUpdatesTo(postAdapter)
    }

    override fun onResume() {
        super.onResume()
        viewModel::updateData
    }

}
