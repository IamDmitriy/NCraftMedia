package com.easyapps.ncraftmedia

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.easyapps.ncraftmedia.model.AttachmentType
import java.util.*

object NotificationHelper {
    private const val STATUS_CHANNEL_ID = "status"
    private var channelCreated = false
    private var lastNotificationID: Int? = null

    fun attachmentUploaded(type: AttachmentType, context: Context) {
        val builder = NotificationCompat.Builder(context, STATUS_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.attachment_uploaded))
            .setContentText("Your ${type.name.toLowerCase()} successfully uploaded.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        showNotificationFromBuilder(builder, context)
    }

    fun comeBack(context: Context) {
        val builder = NotificationCompat.Builder(context, STATUS_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.did_you_like_our_application))
            .setContentText(context.getString(R.string.come_back_please_message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        showNotificationFromBuilder(builder, context)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Attachment uploading"
            val descriptionText = "Notifies when media upload during post creation"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(STATUS_CHANNEL_ID, name, importance)
                .apply {
                    description = descriptionText
                }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }

    }

    private fun createNotificationChannelIfNotCreated(context: Context) {
        if (!channelCreated) {
            createNotificationChannel(context)
            channelCreated = true
        }
    }

    private fun showNotificationFromBuilder(
        builder: NotificationCompat.Builder,
        context: Context
    ) {
        createNotificationChannelIfNotCreated(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.priority = NotificationManager.IMPORTANCE_HIGH
        }

        with(NotificationManagerCompat.from(context)) {
            val notificationId = Random().nextInt(100000)
            lastNotificationID = notificationId
            notify(notificationId, builder.build())
        }
    }
}