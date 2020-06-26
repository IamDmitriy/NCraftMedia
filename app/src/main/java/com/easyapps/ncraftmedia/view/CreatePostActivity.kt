package com.easyapps.ncraftmedia.view

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easyapps.ncraftmedia.App
import com.easyapps.ncraftmedia.R
import com.easyapps.ncraftmedia.model.PostModel
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class CreatePostActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private val repo = App.postRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        val progressDialog = ProgressDialog(this@CreatePostActivity).apply {
            setMessage(getString(R.string.please_wait_message))
            setTitle(getString(R.string.sending_post_message))
            setCancelable(false)
            isIndeterminate = true
        }

        btnSend.setOnClickListener {

            val content = etContent.text.toString()
            if (content.isEmpty()) {
                showToast(getString(R.string.please_input_text_post))
                return@setOnClickListener
            }

            launch {
                progressDialog.show()

                val newPost = PostModel(
                    content = content,
                    created = System.currentTimeMillis(),
                    author = repo.getUserAuth().login
                )
                try {
                    repo.create(newPost)
                } catch (e: Exception) {
                    showToast(getString(R.string.something_went_wrong))
                } finally {
                    progressDialog.hide()
                }

                finish()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@CreatePostActivity, message, Toast.LENGTH_SHORT).show()
    }
}
