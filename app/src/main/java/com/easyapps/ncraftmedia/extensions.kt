package com.easyapps.ncraftmedia

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

public fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}