package com.example.lab26

import android.graphics.Color
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

class ColorAnalyzer(private val listener: (String) -> Unit) : ImageAnalysis.Analyzer {

    override fun analyze(image: ImageProxy) {
        val buffer = image.planes[0].buffer
        val data = ByteArray(buffer.remaining())
        buffer.get(data)

        val y = data[image.width / 2 + image.height / 2 * image.width].toInt() and 0xFF

        val color = Color.rgb(y, y, y)
        val colorHex = String.format("#%06X", 0xFFFFFF and color)

        listener(colorHex)
        image.close()
    }
}
