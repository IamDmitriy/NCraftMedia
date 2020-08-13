package com.easyapps.ncraftmedia.view

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.easyapps.ncraftmedia.App
import com.easyapps.ncraftmedia.NotifictionHelper
import com.easyapps.ncraftmedia.R
import com.easyapps.ncraftmedia.model.AttachmentModel
import com.easyapps.ncraftmedia.showToast
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class CreatePostActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private val repo = App.postRepository
    private val REQUEST_IMAGE_CAPTURE = 1
    private var attachmentModel: AttachmentModel? = null

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
                repo.createPost(content = content, attachment = attachmentModel)
                progressDialog.hide()
                finish()
            }
        }
        btnAttachPhoto.setOnClickListener{
            setUploadingAttachmentStatus()
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun setUploadingAttachmentStatus() {
        btnAttachPhoto.isEnabled = false
        btnAttachPhoto.setImageDrawable(getDrawable(R.drawable.ic_attach_a_photo_inactive_48dp))

        btnSend.isEnabled = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap?
            if (imageBitmap == null) {
                showToast(getString(R.string.something_went_wrong))
                setEmptyAttachmentStatus()
                return
            }

            launch {
                attachmentModel = repo.upload(imageBitmap)
                setAttachmentUploadedStatus()
            }
        }
    }

    private fun setEmptyAttachmentStatus() {
        btnAttachPhoto.isEnabled = true
        btnAttachPhoto.setImageDrawable(getDrawable(R.drawable.ic_attach_a_photo_48dp))

        btnSend.isEnabled = true
    }

    private fun setAttachmentUploadedStatus() {
        btnAttachPhoto.isEnabled = false
        btnAttachPhoto.setImageDrawable(getDrawable(R.drawable.ic_attach_a_photo_uploaded_24dp))

        btnSend.isEnabled = true

        NotifictionHelper.attachmentUploaded(attachmentModel!!.type, this)
    }

}
