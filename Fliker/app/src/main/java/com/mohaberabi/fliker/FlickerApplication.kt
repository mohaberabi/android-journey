package com.mohaberabi.fliker

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.content.getSystemService
import com.mohaberabi.fliker.core.di.AppModule
import com.mohaberabi.fliker.core.di.DefaultAppModule

class FlickerApplication : Application() {
    companion object {
        lateinit var appModule: AppModule
        const val CHANNEL_NAME = "channel"
    }

    override fun onCreate() {
        super.onCreate()
        appModule = DefaultAppModule()
        createNotificationChannel()
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 33) {
            val notificationManager = getSystemService<NotificationManager>()!!
            val channel =
                NotificationChannel(
                    CHANNEL_NAME,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            notificationManager.createNotificationChannel(channel)

        }
    }
}