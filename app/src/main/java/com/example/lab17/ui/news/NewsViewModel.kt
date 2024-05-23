package com.example.lab17.ui.news

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.badge.BadgeDrawable

class NewsViewModel : ViewModel() {
    val timerLiveData: MutableLiveData<CountDownTimer> by lazy {
        MutableLiveData<CountDownTimer>()
    }
    val badgeLiveData: MutableLiveData<BadgeDrawable> by lazy {
        MutableLiveData<BadgeDrawable>()
    }
}
