package com.easyapps.ncraftmedia.api

import com.easyapps.ncraftmedia.error.AuthException
import com.easyapps.ncraftmedia.error.PostNotFoundException
import okhttp3.Interceptor
import okhttp3.Response

const val AUTH_TOKEN_HEADER = "Authorization"

class InjectAuthTokenInterceptor(val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val requestWithToken = originalRequest.newBuilder()
            .header(AUTH_TOKEN_HEADER, "Bearer $authToken")
            .build()

        val response = chain.proceed(requestWithToken)

        if (!response.isSuccessful) {
            checkResponseCode(response.code)
        }

        return response
    }

    private fun checkResponseCode(code: Int) {
        when (code) {
            401 -> throw AuthException()
            //TODO *** -> throw PostNotFoundException()
        }
    }
}