package com.example.lab24

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill

@Composable
fun DrawUFO(x: Float, y: Float, scale: Float) {
    Canvas(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {
        drawUFO(this, x, y, scale)
    }
}

fun drawUFO(drawScope: DrawScope, x: Float, y: Float, scale: Float) {
    with(drawScope) {
        val a = 60f * scale
        val b = 30f * scale

        val gradient = Brush.linearGradient(
            start = Offset(x - 10 * scale, y),
            end = Offset(x + 35 * scale, y),
            colors = listOf(Color(0xFFADD8E6), Color(0xCC000000))
        )

        // Draw the main body of the UFO
        drawOval(
            color = Color.Gray,
            topLeft = Offset(x - a, y - b),
            size = Size(a * 2, b * 2),
            style = Fill,
        )

        // Draw the gradient part of the UFO
        val path = Path().apply {
            moveTo(x, y)
            arcTo(
                rect = Rect(
                    Offset(x - 30 * scale, y - 40 * scale),
                    Size(60f * scale, 80f * scale)
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 180f,
                forceMoveTo = false
            )
            close()
        }

        drawPath(
            path = path,
            brush = gradient,
            style = Fill
        )
    }
}

@Preview
@Composable
fun PreviewDrawUFO() {
    MaterialTheme {
        Surface {
            DrawUFO(x = 100f, y = 100f, scale = 1f)
        }
    }
}
