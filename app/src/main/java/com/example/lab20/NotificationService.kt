package com.example.lab20

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

class NotificationService(private val context: Context) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(error: String?) {

        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val clearColorPendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            Intent(
                "com.example.lab20.CLEAR_COLOR",
                null,
                context,
                NotificationReceiver::class.java
            ),
            PendingIntent.FLAG_IMMUTABLE
        )

        val changeColorPendingIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(
                "com.example.lab20.SET_NEW_COLOR",
                null,
                context,
                NotificationReceiver::class.java
            ),
            PendingIntent.FLAG_MUTABLE
        )
        val remoteInput = RemoteInput.Builder(KEY_TEXT).setLabel("RRGGBB").build()
        val myTextAction = NotificationCompat.Action.Builder(
            R.drawable.ic_launcher_foreground,
            "Задать цвет",
            changeColorPendingIntent
        )
            .addRemoteInput(remoteInput) // crash
            .build()

        val builder = NotificationCompat.Builder(context, CHANNEL)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Новое уведомление")
            .setContentText(if (error != null) error else "Покрасьте ваш экран без смс и регистрации")
            .addAction(R.drawable.ic_launcher_foreground, "Сбросить цвет", clearColorPendingIntent)
            .addAction(myTextAction)
            .setContentIntent(pendingIntent)

        val notification = builder.build()
        notificationManager.notify(1, notification)

    }

    fun clearColor() {
        val updateIntent = Intent("com.example.lab20.UPDATE_COLOR").apply {
            putExtra("color", "#ffffff")
        }
        context.sendBroadcast(updateIntent)
    }
    fun setNewColor(color: String) {
        Log.d("@@", color)
        val updateIntent = Intent("com.example.lab20.UPDATE_COLOR").apply {
            putExtra("color", color)
        }
        context.sendBroadcast(updateIntent)
    }

    fun isHexCode(color: String): Boolean {
        return  color.matches("[]A-Fa-f0-9]{6}".toRegex())
    }

    companion object {
        const val CHANNEL = "notification_channel"
        const val KEY_TEXT = "key_text"

    }
}