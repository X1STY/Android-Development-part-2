package com.example.lab20

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NotificationService.CHANNEL,
            "Сообщения",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Уведомление о входящих сообщениях"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}