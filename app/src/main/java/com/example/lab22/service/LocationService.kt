package com.example.lab22.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class LocationService(
    private val context: Context,
    private val locationListener: LocationListener
) {
    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var randomPoint: Location? = null

    fun getRandomPoint(): Location? {
        return randomPoint
    }
    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                3000L,
                10f,
                locationListener
            )
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                3000L,
                10f,
                locationListener
            )
        }
    }

    fun stopLocationUpdates() {
        locationManager.removeUpdates(locationListener)
    }


    fun generateRandomPoint(): Location? {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val lastKnownLocation =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            lastKnownLocation?.let {
                val radius = 2500 // 2.5 km
                val earthAngleInMeters = 111000.0
                val randomDistance = Random.nextDouble(0.0, radius.toDouble())
                val randomAngle = Random.nextDouble(0.0, 2 * Math.PI)
                val deltaLat = randomDistance * cos(randomAngle) / earthAngleInMeters
                val deltaLon =
                    randomDistance * sin(randomAngle) / (earthAngleInMeters * cos(Math.toRadians(it.latitude)))
                val newLat = it.latitude + deltaLat
                val newLon = it.longitude + deltaLon
                randomPoint = Location(LocationManager.GPS_PROVIDER).apply {
                    latitude = newLat
                    longitude = newLon
                }
                return randomPoint
            }

        }
        return null
    }
    fun calculateDistance(): Double {
        val start = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val end = randomPoint
        if (start == null || end == null) {
            return Double.NaN
        }

        val d = 6371e3
        val phi1 = Math.toRadians(start.latitude)
        val phi2 = Math.toRadians(end.latitude)
        val deltaPhi = Math.toRadians(end.latitude - start.latitude)
        val deltaLambda = Math.toRadians(end.longitude - start.longitude)

        val a = sin(deltaPhi / 2) * sin(deltaPhi / 2) +
                cos(phi1) * cos(phi2) *
                sin(deltaLambda / 2) * sin(deltaLambda / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return d * c
    }

}