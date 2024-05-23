package com.example.lab21.service

import android.app.*
import android.content.*
import android.os.*
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.example.lab21.MainActivity
import com.example.lab21.R
import java.util.Timer

class TimerService : LifecycleService() {

    private var timer: CountDownTimer? = null
    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            ACTIONS.START.toString() -> startTimer()
            ACTIONS.STOP.toString() -> stopTimer()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startTimer() {
        val timerClass = TimerClass()
        val duration = timerClass.getMilisecondsFromMinutesAndSeconds()
        if (!TimerClass.TimerObject.isRunning.value!!) {
            timer = object : CountDownTimer(duration, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val minutesLeft = timerClass.getMinutesFromMiliseconds(millisUntilFinished)
                    val secondsLeft = timerClass.getSecondsFromMiliseconds(millisUntilFinished)
                    TimerClass.TimerObject.setMinutesAndSeconds(minutesLeft, secondsLeft)
                    displayNotification()
                }

                override fun onFinish() {
                    displayNotification()
                    stopSelf()
                }
            }
            timer?.start()
            TimerClass.TimerObject.setIsRunning(true)
        }
    }

    private fun stopTimer() {
        timer?.cancel()
        TimerClass.TimerObject.setIsRunning(false)
        stopSelf()
    }


    private fun displayNotification() {

        val minutes = TimerClass.TimerObject.minutes.value
        val seconds = TimerClass.TimerObject.seconds.value
        val out = String.format("%02d:%02d", minutes, seconds)
        val notificationIntent =  Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            3,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, "timer_notification_channel")
            .setContentTitle("Time Left")
            .setContentText(out)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
    }

    enum class ACTIONS {
        START, STOP
    }
}