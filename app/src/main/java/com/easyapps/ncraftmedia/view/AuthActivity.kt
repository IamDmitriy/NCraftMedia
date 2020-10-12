package com.easyapps.ncraftmedia.view

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.easyapps.ncraftmedia.*
import com.easyapps.ncraftmedia.error.AuthException
import com.easyapps.ncraftmedia.model.User
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class AuthActivity : AppCompatActivity(R.layout.activity_auth), CoroutineScope by MainScope() {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var progressDialog: ProgressDialog
    private val repo = App.postRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        requestToken()

        if (isAuthenticated()) {
            repo.createRetrofitWithAuth(
                sharedPref.getString(TOKEN_KEY, "")!!,
                User(sharedPref.getString(USER_AUTH_KEY, "")!!)
            )
            goToFeed()
            return
        }
    }

    private fun init() {
        sharedPref = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

        progressDialog = ProgressDialog(this@AuthActivity).apply {
            setMessage(getString(R.string.please_wait_message))
            setTitle(getString(R.string.login_message))
            setCancelable(false)
            isIndeterminate = true
        }

        btnLogin.setOnClickListener {
            launch {

                progressDialog.show()

                try {
                    val token = repo.authenticate(
                        etLogin.text.toString(),
                        etPassword.text.toString()
                    )

                    saveAuthData(token.token, etLogin.text.toString())

                } catch (e: AuthException) {
                    showToast(getString(R.string.auth_exception))
                    return@launch
                }
                progressDialog.hide()
                showToast(getString(R.string.welcome))
                goToFeed()
            }
        }

        btnRegistration.setOnClickListener {
            startActivity(
                Intent(this@AuthActivity, RegistrationActivity::class.java)
            )
        }
    }

    private fun requestToken() {
        with(GoogleApiAvailability.getInstance()) {
            val code = isGooglePlayServicesAvailable(this@AuthActivity)
            if (code == ConnectionResult.SUCCESS) {
                return@with
            }

            if (isUserResolvableError(code)) {
                getErrorDialog(this@AuthActivity, code, 9000).show()
                return
            }

            showToast(getString(R.string.google_play_unavailable))
        }

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            launch {
                println(it.token)
                    // TODO:
                    // Repository.registerPushToken(it.token)
            }
        }
    }

    private fun isAuthenticated(): Boolean {
        val isStartedWithAuthError = intent.getBooleanExtra(IS_STARTED_WITH_AUTH_ERROR_KEY, false)
        return hasAuthData() && !isStartedWithAuthError
    }

    private fun hasAuthData(): Boolean = sharedPref.contains(TOKEN_KEY)

    private fun saveAuthData(token: String, login: String) {
        sharedPref.edit {
            putString(TOKEN_KEY, token)
            putString(USER_AUTH_KEY, login)
        }
    }

    private fun goToFeed() {
        startActivity(Intent(this, FeedActivity::class.java))
        finish()
    }

    override fun onStop() {
        super.onStop()
        progressDialog.hide()
        coroutineContext.cancelChildren()
    }
}
