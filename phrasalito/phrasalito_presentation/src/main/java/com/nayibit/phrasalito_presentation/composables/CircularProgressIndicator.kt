package com.nayibit.phrasalito_presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nayibit.phrasalito_presentation.ui.theme.primaryGradientEnd
import com.nayibit.phrasalito_presentation.ui.theme.primaryGradientStart


@Composable
fun CircularProgressIndicator(
    progress: Float,                 // 0f..1f
    modifier: Modifier = Modifier,
    sizePercentage: Float = 0.4f,    // 40% of available space by default
    strokeWidthPercentage: Float = 0.08f, // 8% of circle size for stroke width
    backgroundColor: Color = Color.LightGray.copy(alpha = 0.3f),
    progressColor: List<Color> = listOf(primaryGradientStart, primaryGradientEnd),
    textColor: Color = MaterialTheme.colorScheme.inversePrimary,
    textSizePercentage: Float = 0.15f // 15% of circle size for text
) {
    // Clamp progress between 0f and 1f
    val safeProgress = progress.coerceIn(0f, 1f)

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        // Calculate size based on available space
        val availableSize = minOf(maxWidth, maxHeight)
        val circleSize = availableSize * sizePercentage
        val strokeWidth = circleSize * strokeWidthPercentage
        val textSize = (circleSize.value * textSizePercentage).sp

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(circleSize)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val sweepAngle = safeProgress * 360f
                val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                val brush = Brush.linearGradient(
                    colors = progressColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, size.height)
                )

                // Background circle
                drawArc(
                    color = backgroundColor,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = stroke
                )

                // Progress arc
                drawArc(
                    brush = brush,
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = stroke
                )
            }

            // Text in the middle
            Text(
                text = "${(safeProgress * 100).toInt()}%",
                color = textColor,
                fontSize = textSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}