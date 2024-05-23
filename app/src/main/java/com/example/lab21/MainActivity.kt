package com.example.lab21

import android.Manifest.permission.FOREGROUND_SERVICE
import android.Manifest.permission.FOREGROUND_SERVICE_SPECIAL_USE
import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.*
import android.os.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab21.databinding.ActivityMainBinding
import com.example.lab21.service.TimerClass
import com.example.lab21.service.TimerService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(POST_NOTIFICATIONS, FOREGROUND_SERVICE, FOREGROUND_SERVICE_SPECIAL_USE),
                0
            )
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (TimerClass.TimerObject.isRunning.value!!) {
            TimerClass.TimerObject.seconds.observe(this) {
                val minutes = TimerClass.TimerObject.minutes.value ?: 0
                val seconds = TimerClass.TimerObject.seconds.value ?: 0
                binding.textView2.text = String.format("%02d:%02d", minutes, seconds)
                binding.editTextMinutes.isEnabled = false
                binding.editTextSeconds.isEnabled = false
                binding.buttonStartStop.text = getString(R.string.stop_string)
            }
        }

        binding.buttonStartStop.setOnClickListener {
            if (binding.buttonStartStop.text == getString(R.string.start_string)) {
                val minutesTV = binding.editTextMinutes.text.toString().toIntOrNull() ?: 0
                val secondsTV = binding.editTextSeconds.text.toString().toIntOrNull() ?: 0
                if (minutesTV == 0 && secondsTV == 0) {
                    binding.textView2.text = "Неверный ввод данных"
                    return@setOnClickListener
                }
                intent = Intent(this, TimerService::class.java).apply {
                    action = TimerService.ACTIONS.START.toString()
                }
                TimerClass.TimerObject.setMinutesAndSeconds(minutesTV, secondsTV)
                startService(intent)
                TimerClass.TimerObject.seconds.observe(this) {
                    val minutes = TimerClass.TimerObject.minutes.value ?: 0
                    val seconds = TimerClass.TimerObject.seconds.value ?: 0
                    binding.textView2.text = String.format("%02d:%02d", minutes, seconds)
                    binding.editTextMinutes.isEnabled = false
                    binding.editTextSeconds.isEnabled = false
                    binding.buttonStartStop.text = getString(R.string.stop_string)
                }
            } else
            if (binding.buttonStartStop.text == getString(R.string.stop_string)) {
                intent = Intent(this, TimerService::class.java).apply {
                    action = TimerService.ACTIONS.STOP.toString()
                }
                startService(intent)
                binding.editTextMinutes.isEnabled = true
                binding.editTextSeconds.isEnabled = true
                binding.buttonStartStop.text = getString(R.string.start_string)
                binding.textView2.text = "Таймер готов к работе"
            }
        }

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
