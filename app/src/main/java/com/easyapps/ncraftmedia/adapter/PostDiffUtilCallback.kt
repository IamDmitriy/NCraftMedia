package com.easyapps.ncraftmedia.adapter

import androidx.recyclerview.widget.DiffUtil
import com.easyapps.ncraftmedia.model.PostModel

class PostDiffUtilCallback(
    private val oldList: List<PostModel>,
    private val newList: List<PostModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPost: PostModel = oldList[oldItemPosition]
        val newPost: PostModel = newList[newItemPosition]
        return oldPost.id == newPost.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPost: PostModel = oldList[oldItemPosition]
        val newPost: PostModel = newList[newItemPosition]
        return (
                oldPost.author == newPost.author &&
                        oldPost.content == newPost.content &&
                        oldPost.created == newPost.created &&
                        oldPost.likedByMe == newPost.likedByMe &&
                        oldPost.countLikes == newPost.countLikes &&
                        oldPost.commentedByMe == newPost.commentedByMe &&
                        oldPost.countComments == newPost.countComments &&
                        oldPost.sharedByMe == newPost.sharedByMe &&
                        oldPost.countShares == newPost.countShares
                )
    }
}
