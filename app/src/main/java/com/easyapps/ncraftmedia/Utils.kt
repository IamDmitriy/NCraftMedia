package com.easyapps.ncraftmedia

import java.util.regex.Pattern

fun isValidPassword(password: String) =
    Pattern.compile("(?=.*[A-Z])(?!.*[^a-zA-Z0-9])(.{6,})\$").matcher(password).matches()

fun isValidLogin(login: String) =
    Pattern.compile("(?!.*[^a-zA-Z0-9])(.{2,})\$").matcher(login).matches()