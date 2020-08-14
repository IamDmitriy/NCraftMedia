package com.easyapps.ncraftmedia

import android.content.Context
import java.util.regex.Pattern

fun isValidPassword(password: String) =
    Pattern.compile("(?=.*[A-Z])(?!.*[^a-zA-Z0-9])(.{6,})\$").matcher(password).matches()

fun isValidLogin(login: String) =
    Pattern.compile("(?!.*[^a-zA-Z0-9])(.{2,})\$").matcher(login).matches()

fun isFirstUse(context: Context): Boolean {
    return context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        .getLong(LAST_TIME_VISIT_SHARED_KEY, 0) == 0L
}

fun setNowLastVisitTime(context: Context) {
    context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        .edit()
        .putLong(LAST_TIME_VISIT_SHARED_KEY, System.currentTimeMillis())
        .apply()
}