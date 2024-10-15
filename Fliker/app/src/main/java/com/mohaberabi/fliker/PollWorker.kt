package com.mohaberabi.fliker

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class PollWorker(
    private val context: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {

        return if (checkPermission()) {
            notifyUser()
            Result.success()
        } else {
            Result.failure()
        }
    }


    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= 33) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

    }

    private fun notifyUser() {
        val intent = MainActivity.newIntent(context)
        val pendingIntent = PendingIntent.getActivity(
            context,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val noti = NotificationCompat.Builder(context, FlickerApplication.CHANNEL_NAME)
            .setTicker("New Picture Fetched ")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("We Fetched a new Photos")
            .setContentText("check them now ")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val manager = requireNotNull(context.getSystemService<NotificationManager>())
        manager.notify(1, noti)

    }
}