package com.example.lab17

import android.os.Bundle
import android.os.CountDownTimer
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lab17.databinding.ActivityMainBinding
import com.example.lab17.ui.news.NewsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var timer: CountDownTimer

    private val newsViewModel by lazy {
        ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_music, R.id.navigation_book, R.id.navigation_news
            )
        )

        val badge = navView.getOrCreateBadge(R.id.navigation_news)

        timer = object : CountDownTimer(100000000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                badge.isVisible = true
                badge.number++
            }
            override fun onFinish() {
                badge.number = 0
            }
        }

        // Set timerLiveData value in ViewModel
        newsViewModel.timerLiveData.value = timer
        newsViewModel.badgeLiveData.value = badge
        timer.start()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
