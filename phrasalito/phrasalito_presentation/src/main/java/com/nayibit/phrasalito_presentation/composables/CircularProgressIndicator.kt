package com.nayibit.phrasalito_presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularProgressIndicator(
    progress: Float,                 // 0f..1f
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,               // overall size
    strokeWidth: Dp = 10.dp,         // thickness
    backgroundColor: Color = Color.LightGray.copy(alpha = 0.3f),
    progressColor: List<Color> = listOf(Color.Blue, Color.Cyan),
    textColor: Color = Color.Black,
    textSize: TextUnit = 20.sp
) {
    // Clamp progress between 0f and 1f
    val safeProgress = progress.coerceIn(0f, 1f)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            val sweepAngle = safeProgress * 360f
            val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)

            val brush = Brush.linearGradient(
                colors = progressColor,
                start = Offset(0f, 0f),
                end = Offset(size.toPx(), size.toPx())
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