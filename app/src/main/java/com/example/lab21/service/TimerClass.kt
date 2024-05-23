package com.example.lab21.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lab21.service.TimerClass.TimerObject.minutes
import com.example.lab21.service.TimerClass.TimerObject.seconds


class TimerClass {

    object TimerObject {
        private val _minutes = MutableLiveData<Int>()
        val minutes: LiveData<Int>
            get() = _minutes

        private val _seconds = MutableLiveData<Int>()
        val seconds: LiveData<Int>
            get() = _seconds

        private val _isRunning = MutableLiveData<Boolean>()
        val isRunning: LiveData<Boolean>
            get() = _isRunning

        init {
            _minutes.value = 0
            _seconds.value = 0
            _isRunning.value = false
        }

        fun setMinutesAndSeconds(minutes: Int, seconds: Int) {
            _minutes.value = minutes
            _seconds.value = seconds
        }

        fun setIsRunning(isRunning: Boolean) {
            _isRunning.value = isRunning
        }

    }

    fun getMilisecondsFromMinutesAndSeconds(): Long {
        return ((minutes.value!! * 60 + seconds.value!!) * 1000).toLong()
    }

    fun getMinutesFromMiliseconds(miliseconds: Long): Int {
        return ((miliseconds / 1000) / 60).toInt()
    }

    fun getSecondsFromMiliseconds(miliseconds: Long): Int {
        return ((miliseconds / 1000) % 60).toInt()
    }

}