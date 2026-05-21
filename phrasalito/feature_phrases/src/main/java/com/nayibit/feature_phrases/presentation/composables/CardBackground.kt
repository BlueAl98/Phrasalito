package com.nayibit.feature_phrases.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import com.nayibit.utils.ui.theme.primaryGradientStart

@Composable
fun CardBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val bg = MaterialTheme.colorScheme.surfaceVariant
    val bgDark = lerp(bg, Color.Black, 0.18f)
    val bgLight = lerp(bg, Color.White, 0.10f)
    val accentTop = lerp(bg, Color.White, 0.50f)
    val accentBottom = lerp(bg, Color.White, 0.28f)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .drawBehind {
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(bgDark, bgLight),
                        start = Offset.Zero,
                        end = Offset(size.width, size.height)
                    )
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(accentTop.copy(alpha = 0.35f), Color.Transparent),
                        center = Offset(x = size.width * 0.85f, y = size.height * 0.15f),
                        radius = size.minDimension * 0.45f
                    ),
                    radius = size.minDimension * 0.45f,
                    center = Offset(x = size.width * 0.85f, y = size.height * 0.15f)
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(accentBottom.copy(alpha = 0.40f), Color.Transparent),
                        center = Offset(x = size.width * 0.15f, y = size.height * 0.85f),
                        radius = size.minDimension * 0.50f
                    ),
                    radius = size.minDimension * 0.50f,
                    center = Offset(x = size.width * 0.15f, y = size.height * 0.85f)
                )
            }
    ) {
        content()
    }
}

