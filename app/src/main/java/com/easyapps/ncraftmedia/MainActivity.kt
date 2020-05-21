package com.easyapps.ncraftmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.easyapps.ncraftmedia.adapter.PostAdapter
import com.easyapps.ncraftmedia.adapter.PostDiffUtilCallback
import com.easyapps.ncraftmedia.viewmodel.MainViewModel
import com.easyapps.ncraftmedia.viewmodel.UiState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val viewModel: MainViewModel by viewModels()
        val postAdapter = PostAdapter(viewModel)

        with(rvContainer) {
            layoutManager = LinearLayoutManager(this@MainActivity)
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

                    val postDiffUtilCallback =
                        PostDiffUtilCallback(postAdapter.postList, state.posts)
                    val postDiffResult = DiffUtil.calculateDiff(postDiffUtilCallback)
                    postAdapter.postList = state.posts
                    postDiffResult.dispatchUpdatesTo(postAdapter)
                }

                is UiState.EmptyProgress -> {
                    tvErrorMessage.isVisible = false
                    btnRepeat.isVisible = false
                    progressBar.isVisible = true
                    swipeRefreshLayout.isRefreshing = false
                    rvContainer.isVisible = false
                }

                is UiState.Error -> {
                    tvErrorMessage.isVisible = true
                    tvErrorMessage.text = getString(R.string.message_no_internet_connection)
                    btnRepeat.isVisible = true
                    progressBar.isVisible = false
                    swipeRefreshLayout.isRefreshing = false
                    rvContainer.isVisible = false
                }

                is UiState.Success -> {
                    tvErrorMessage.isVisible = false
                    btnRepeat.isVisible = false
                    progressBar.isVisible = false
                    swipeRefreshLayout.isRefreshing = false
                    rvContainer.isVisible = true

                    val postDiffUtilCallback =
                        PostDiffUtilCallback(postAdapter.postList, state.posts)
                    val postDiffResult = DiffUtil.calculateDiff(postDiffUtilCallback)
                    postAdapter.postList = state.posts
                    postDiffResult.dispatchUpdatesTo(postAdapter)
                }
            }

        }

        swipeRefreshLayout.setOnRefreshListener(viewModel::updateData)
        btnRepeat.setOnClickListener {
            viewModel.loadData()
        }
    }

}
