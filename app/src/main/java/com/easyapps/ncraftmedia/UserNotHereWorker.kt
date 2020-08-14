package com.easyapps.ncraftmedia

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class UserNotHereWorker(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {
    val context = context
    override fun doWork(): Result {
        //TODO ? если пользователь заходил меньше SHOW_NOTIFICATION_AFTER_UNVISITED_MS времени, то показываем нотификацию
        Log.d("MyTag", "Должна была всплыть нотификация, что вы давно не заходили")
        NotificationHelper.comeBack(context)
        return Result.success()
    }
}