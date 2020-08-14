package com.easyapps.ncraftmedia

import android.content.Context
import java.util.regex.Pattern

fun isValidPassword(password: String) =
    Pattern.compile("(?=.*[A-Z])(?!.*[^a-zA-Z0-9])(.{6,})\$").matcher(password).matches()

fun isValidLogin(login: String) =
    Pattern.compile("(?!.*[^a-zA-Z0-9])(.{2,})\$").matcher(login).matches()

fun isFirstUse(context: Context): Boolean {
    return context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        .getBoolean(IS_FIRST_USE_KEY, true)
}

fun setNotFirstUse(context: Context) {
    context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        .edit()
        .putBoolean(IS_FIRST_USE_KEY, false)
        .apply()
}