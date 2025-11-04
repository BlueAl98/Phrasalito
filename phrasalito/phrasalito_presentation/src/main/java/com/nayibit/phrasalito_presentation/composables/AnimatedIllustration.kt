package com.nayibit.phrasalito_presentation.composables


import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.nayibit.phrasalito_presentation.model.OnboardingColors
import com.nayibit.phrasalito_presentation.ui.theme.primaryGradientEnd
import com.nayibit.phrasalito_presentation.ui.theme.primaryGradientStart


@Composable
fun AnimatedIllustration(
    modifier: Modifier = Modifier,
    colors: OnboardingColors,
    mainImageVector: ImageVector = Icons.Default.Check,
    topIconVector: ImageVector = Icons.Default.Star,
    bottomIconVector: ImageVector = Icons.Default.QuestionMark
    ) {
    val infiniteTransition = rememberInfiniteTransition(label = "illustration")

    // Float animation for main container
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -20f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    // Pulse animation for background circle
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    // Floating elements animation
    val floatSideOffset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatSide1"
    )

    val floatSideOffset2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOut, delayMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatSide2"
    )

    Box(
        modifier = Modifier
            .size(280.dp)
            .offset(y = floatOffset.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background Circle with pulse
        Box(
            modifier = modifier
                .fillMaxSize()
                .scale(pulseScale)
                .alpha(pulseAlpha)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            primaryGradientStart.copy(alpha = 0.2f),
                            primaryGradientEnd.copy(alpha = 0.4f)
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Icon Group
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Floating element 1 (top left)
            FloatingElement(
                icon = topIconVector,
                iconColor = colors.accentYellow,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(
                        x = (20 + floatSideOffset1).dp,
                        y = (50 - floatSideOffset1).dp
                    )
            )

            // Main Icon
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                primaryGradientStart,
                                primaryGradientEnd
                            )
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = mainImageVector,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }

            // Floating element 2 (bottom right)
            FloatingElement(
                icon = bottomIconVector,
                iconColor = primaryGradientStart,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(
                        x = (-20 + floatSideOffset2).dp,
                        y = (-50 - floatSideOffset2).dp
                    )
            )
        }
    }
}


















