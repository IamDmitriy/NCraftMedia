package com.easyapps.ncraftmedia.view

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.easyapps.ncraftmedia.*
import com.easyapps.ncraftmedia.error.AuthException
import com.easyapps.ncraftmedia.model.PostModel
import com.easyapps.ncraftmedia.model.PostType
import kotlinx.android.synthetic.main.activity_repost.*
import kotlinx.android.synthetic.main.post_card.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import java.nio.channels.UnresolvedAddressException

class RepostActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private val repo = App.postRepository
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repost)

        initRepostContainerByIntent()

        progressDialog = ProgressDialog(this@RepostActivity).apply {
            setMessage(getString(R.string.please_wait_message))
            setTitle(getString(R.string.sending))
            setCancelable(false)
            isIndeterminate = true
        }

        btnRepost.setOnClickListener {
            launch {
                progressDialog.show()
                tryRepost()
                progressDialog.hide() //TODO поведение при завершении активности
            }
        }
    }

    private suspend fun tryRepost() {
        try {
            repo.repost(getAndCheckRepostedId(), etContent.text.toString())
            finish()
        } catch (e: AuthException) {
            goToAuth()
        } catch (e: UnresolvedAddressException) {
            showToast(getString(R.string.message_no_internet_connection))
        }
    }

    private fun initRepostContainerByIntent() {
        val repostedId = getAndCheckRepostedId()

        launch {
            progressBar.isVisible = true
            val reposted = repo.getById(repostedId)
            if (reposted!!.type == PostType.REPOST) {
                showToast("Нельзя репостить репосты!")
                finish()
            }

            initRepostContainer(reposted)

            progressBar.isVisible = false
        }
    }

    private fun initRepostContainer(reposted: PostModel) {
        val view = LayoutInflater.from(this).inflate(R.layout.post_card, repostContainer, false)

        val tvContent = view.findViewById<TextView>(R.id.tvContent)!!
        val tvAuthor = view.findViewById<TextView>(R.id.tvAuthor)
        val tvCreated = view.findViewById<TextView>(R.id.tvCreated)

        tvContent.text = reposted.content
        tvAuthor.text = reposted.author
        tvCreated.text = publishedAgoInMillisToTimeInWords(
            System.currentTimeMillis() - reposted.created
        )

        view.findViewById<ConstraintLayout>(R.id.bottomBar).visibility = View.GONE
        view.findViewById<ImageButton>(R.id.btnHide).visibility = View.GONE
        view.findViewById<ImageButton>(R.id.btnShare).visibility = View.GONE

        repostContainer.addView(view)
        //TODO реализовать инициализацию в зависимости от типа view
    }

    private fun getAndCheckRepostedId(): Long {
        val repostedId = intent.getLongExtra(REPOSTED_ID_KEY, -1)
        if (repostedId == -1L) {
            showToast("Не указано Id поста для репоста")
            finish()
        }
        return repostedId
    }

    override fun onStop() {
        super.onStop()
        coroutineContext.cancelChildren()
    }

    private fun goToAuth() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.putExtra(IS_STARTED_WITH_AUTH_ERROR_KEY, true)
        startActivity(intent)
        finish()
    }

    private fun publishedAgoInMillisToTimeInWords(publishedAgo: Long): String {
        return when (publishedAgo / 1000) {
            in 0..30 -> "менее минуты назад"
            in 31..90 -> "минуту назад"
            in 91..360 -> "6 минут назад"
            in 361..3600 -> "час назад"
            in 3601..7200 -> "2 часа назад"
            in 7201..86_400 -> "несколько часов назад"
            in 86_401..172_800 -> "день назад"
            in 172_801..604_800 -> "несколько дней назад"
            in 604_801..1_209_600 -> "неделю назад"
            in 1_209_601..2_678_400 -> "несколько недель назад"
            in 2_678_401..5_356_800 -> "месяц назад"
            in 5_356_801..32_140_800 -> "несколько месяцев назад"
            in 32_140_801..64_281_600 -> "год назад"
            else -> "несколько лет назад"
        }
    }
}
