package com.example.lab27

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var sensorManager: SensorManager
    private lateinit var gravitySensor: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)!!

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GravityBall(sensorManager, gravitySensor)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            sensorEventListener, gravitySensor, SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(sensorEventListener)

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorEventListener)
    }

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                onGravitySensorChanged(it.values)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    @Composable
    fun GravityBall(sensorManager: SensorManager, gravitySensor: Sensor) {
        var xPosition by remember { mutableStateOf(500f) }
        var yPosition by remember { mutableStateOf(500f) }
        var xVelocity by remember { mutableStateOf(0f) }
        var yVelocity by remember { mutableStateOf(0f) }
        var xAcceleration by remember { mutableStateOf(0f) }
        var yAcceleration by remember { mutableStateOf(0f) }

        val gravityFactor = 9.8f 

        val configuration = LocalConfiguration.current
        val density = LocalDensity.current
        val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
        val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

        LaunchedEffect(sensorManager) {
            val sensorEventListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    event?.let {
                        xAcceleration = it.values[0] * gravityFactor
                        yAcceleration = it.values[1] * gravityFactor
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            sensorManager.registerListener(
                sensorEventListener, gravitySensor, SensorManager.SENSOR_DELAY_GAME
            )

        }

        LaunchedEffect(Unit) {
            while (true) {
                xVelocity += xAcceleration * 0.05f
                yVelocity += yAcceleration * 0.05f

                xPosition += xVelocity * 0.05f
                yPosition += yVelocity * 0.05f

                if (xPosition <= 0f || xPosition >= screenWidthPx) {
                    xVelocity = -xVelocity
                    xPosition = xPosition.coerceIn(0f, screenWidthPx)
                }

                if (yPosition <= 0f || yPosition >= screenHeightPx) {
                    yVelocity = -yVelocity
                    yPosition = yPosition.coerceIn(0f, screenHeightPx)
                }

                delay(16L)
            }
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawCircle(
                color = Color.Red,
                radius = 50f,
                center = Offset(xPosition, yPosition)
            )
        }
    }

    private fun onGravitySensorChanged(values: FloatArray) {
    }
}
