package com.nayibit.phrasalito_presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CardBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .drawBehind {
                // Base gradient background
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF0A1E4A),
                            Color(0xFF0D2E7A)
                        ),
                        start = Offset.Zero,
                        end = Offset(size.width, size.height)
                    )
                )

                // 🔵 Top-right soft circle
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF4A90E2).copy(alpha = 0.35f),
                            Color.Transparent
                        ),
                        center = Offset(
                            x = size.width * 0.85f,
                            y = size.height * 0.15f
                        ),
                        radius = size.minDimension * 0.45f
                    ),
                    radius = size.minDimension * 0.45f,
                    center = Offset(
                        x = size.width * 0.85f,
                        y = size.height * 0.15f
                    )
                )

                // 🟣 Bottom-left soft circle
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF1E4DB7).copy(alpha = 0.4f),
                            Color.Transparent
                        ),
                        center = Offset(
                            x = size.width * 0.15f,
                            y = size.height * 0.85f
                        ),
                        radius = size.minDimension * 0.5f
                    ),
                    radius = size.minDimension * 0.5f,
                    center = Offset(
                        x = size.width * 0.15f,
                        y = size.height * 0.85f
                    )
                )
            }
    ) {
        content()
    }
}

