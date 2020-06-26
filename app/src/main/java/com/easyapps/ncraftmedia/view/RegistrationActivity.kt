package com.easyapps.ncraftmedia.view

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.easyapps.ncraftmedia.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity(R.layout.activity_registration),
    CoroutineScope by MainScope() {
    private lateinit var progressDialog: ProgressDialog
    private lateinit var sharedPref: SharedPreferences
    private val repo = App.postRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

        progressDialog = ProgressDialog(this@RegistrationActivity).apply {
            setMessage(getString(R.string.please_wait_message))
            setTitle(getString(R.string.registration_message))
            setCancelable(false)
            isIndeterminate = true

        }

        btnRegistration.setOnClickListener {
            val login = etLogin.text.toString()
            val password = etPassword.text.toString()
            val passwordConfirmation = etPasswordConfirmation.text.toString()

            when {
                !isValidLogin(login) -> {
                    etLogin.error = "Некорректный логин!"
                    return@setOnClickListener
                }

                password != passwordConfirmation -> {
                    etPasswordConfirmation.error = "Пароли не совпадают!"
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Пароли не совпадают!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                !isValidPassword(password) -> {
                    etPassword.error = "Некорректный пароль"
                    return@setOnClickListener
                }
            }
            launch {
                progressDialog.show()

                val response = repo.register(
                    login,
                    password
                )

                if (response.isSuccessful) {
                    saveToken(requireNotNull(response.body()).token)

                    Toast.makeText(this@RegistrationActivity, "Успех", Toast.LENGTH_SHORT).show()

                    progressDialog.hide()

                    finish()
                }
            }

        }
    }

    private fun saveToken(token: String) {
        sharedPref.edit {
            putString(TOKEN_KEY, token)
        }
    }

    override fun onStop() {
        super.onStop()
        progressDialog.hide()
        coroutineContext.cancelChildren()
    }
}
