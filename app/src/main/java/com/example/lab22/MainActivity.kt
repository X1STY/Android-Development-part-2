package com.example.lab22

import android.Manifest
import android.location.Location
import android.location.LocationListener
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab22.databinding.ActivityMainBinding
import com.example.lab22.service.LocationService

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var locationService: LocationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locationService = LocationService(this, this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )
        }

        binding.buttonGetNewPoint.setOnClickListener {
            val randomPoint = locationService.generateRandomPoint()
            if (randomPoint != null) {
                binding.tvStatus.text = "Точка загадана, ищите!"
                binding.tvStatus.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
                binding.tvDistance.text = "Расстояние: ${String.format("%.2f",locationService.calculateDistance())} м"

            } else {
                Toast.makeText(this, "Не удалось определить местоположение. Пожалуйста, попробуйте снова.", Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonOpenSettings.setOnClickListener {
            val intent = android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = android.net.Uri.parse("package:$packageName")
            startActivity(intent)
        }

        locationService.startLocationUpdates()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationService.stopLocationUpdates()

    }

    override fun onLocationChanged(location: Location) {
        val randomPoint = locationService.getRandomPoint()
        randomPoint?.let {
            val distance = location.distanceTo(randomPoint)
            binding.tvDistance.text = "Расстояние: ${String.format("%.2f", distance)} м"

            if (distance <= 100) {
                binding.tvStatus.text = "Ура, точка найдена!"
                binding.tvStatus.setTextColor(resources.getColor(android.R.color.holo_green_dark))
            } else {
                binding.tvStatus.text = "Точка загадана, ищите!"
                binding.tvStatus.setTextColor(resources.getColor(android.R.color.holo_blue_dark))
            }
        }
    }
}