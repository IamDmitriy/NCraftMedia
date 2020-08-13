package com.easyapps.ncraftmedia

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.easyapps.ncraftmedia.model.AttachmentType
import java.util.*

object NotifictionHelper {
    private val UPLOAD_CHANNEL_ID = "Attachment uploading"
    private var channelCreated = false
    private var lastNotificationID: Int? = null

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Attachment uploading"
            val descriptionText = "Notifies when media upload during post creation"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(UPLOAD_CHANNEL_ID, name, importance)
                .apply {
                    description = descriptionText
                }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }

    }

    fun attachmentUploaded(type: AttachmentType, context: Context) {
        createNotificationChannelIfNotCreated(context)

        val builder = NotificationCompat.Builder(context, UPLOAD_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Attachment uploaded")
            .setContentText("your ${type.name.toLowerCase()} successfully uploaded.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.priority = NotificationManager.IMPORTANCE_HIGH
        }

        with(NotificationManagerCompat.from(context)) {
            val notificationId = Random().nextInt(100000)
            lastNotificationID = notificationId
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannelIfNotCreated(context: Context) {
        if (!channelCreated) {
            createNotificationChannel(context)
            channelCreated = true
        }
    }
}