package com.easyapps.ncraftmedia.adapter.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.easyapps.ncraftmedia.adapter.PostAdapter
import kotlinx.android.synthetic.main.item_load_more.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FooterViewHolder(val adapter: PostAdapter, view: View) : RecyclerView.ViewHolder(view) {
    init {
        with(itemView) {
            loadMoreBtn.setOnClickListener {
                setUpdateState()
                adapter.viewModel.loadMore()
            }
        }
    }

    fun setSuccessfullState() {
        with(itemView) {
            loadMoreBtn.isEnabled = true
            loadMoreBtn.visibility = View.VISIBLE
            progressbar.visibility = View.INVISIBLE
        }
    }

    fun setUpdateState() {
        with(itemView) {
            loadMoreBtn.isEnabled = false
            loadMoreBtn.visibility = View.INVISIBLE
            progressbar.visibility = View.VISIBLE
        }
    }
}