package com.easyapps.ncraftmedia

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService: FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage) {
        println(p0)
    }

    override fun onNewToken(p0: String) {
        println(p0)
    }
}