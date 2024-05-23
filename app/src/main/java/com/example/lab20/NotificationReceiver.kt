package com.example.lab20

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat.getSystemService
import com.example.lab20.NotificationService.Companion.KEY_TEXT
import com.example.lab20.databinding.ActivityMainBinding

class NotificationReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("@", intent?.action.toString())
        val service = NotificationService(context)
        when (intent?.action) {
            "com.example.lab20.CLEAR_COLOR" -> {
                service.clearColor()
            }
            "com.example.lab20.SET_NEW_COLOR" -> {
                val text = RemoteInput.getResultsFromIntent(intent)?.getCharSequence(KEY_TEXT).toString()
                val isHexCode = service.isHexCode(text)
                if (isHexCode) {
                    service.setNewColor("#$text")
                    service.showNotification("Цвет изменен на $text")
                }
                else service.showNotification("Неверный формат ввода текста")
                Log.d("@", text)
            }
        }
    }
}