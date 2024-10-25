import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.rotate


@Composable
fun RocketWithFireAndMovement(startX: Float, startY: Float, k: Float) {
    val infiniteTransition = rememberInfiniteTransition()

    val fireHeight by infiniteTransition.animateFloat(
        initialValue = 20f * k,
        targetValue = 40f * k,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val rocketY by infiniteTransition.animateFloat(
        initialValue = 2000f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(0),
        ), label = ""
    )

    val rocketX by infiniteTransition.animateFloat(
        initialValue = startX,
        targetValue = startX + 300f, // Move the rocket right by 100 pixels scaled by k
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(0),
        ), label = ""
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        rotate(10f, Offset(rocketX, rocketY - 30 * k)) {

            drawRect(
                color = Color.Gray,
                topLeft = Offset(rocketX - 20 * k, rocketY - 60 * k),
                size = Size(40f * k, 60f * k)
            )

            drawPath(
                color = Color.Red,
                path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(rocketX, rocketY - 80 * k)
                    lineTo(rocketX - 20 * k, rocketY - 60 * k)
                    lineTo(rocketX + 20 * k, rocketY - 60 * k)
                    close()
                }
            )

            drawRect(
                color = Color.Blue,
                topLeft = Offset(rocketX - 30 * k, rocketY - 40 * k),
                size = Size(10f * k, 20f * k)
            )
            drawRect(
                color = Color.Blue,
                topLeft = Offset(rocketX + 20 * k, rocketY - 40 * k),
                size = Size(10f * k, 20f * k)
            )

            drawPath(
                color = Color.Yellow,
                path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(rocketX, rocketY)
                    lineTo(rocketX - 10 * k, rocketY + fireHeight)
                    lineTo(rocketX + 10 * k, rocketY + fireHeight)
                    close()
                }
            )
        }

    }
}

@Composable
fun RocketScreen() {
    RocketWithFireAndMovement(startX = 700f, startY = 1600f, k = 2.0f)
}
