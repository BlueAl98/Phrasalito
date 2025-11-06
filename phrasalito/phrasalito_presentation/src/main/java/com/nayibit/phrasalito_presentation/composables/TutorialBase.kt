package com.nayibit.phrasalito_presentation.composables


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp


@Composable
fun TutorialBase(
    listComponents: List<Rect?>,
    isTutorialEnabled: Boolean = true,
    onTutorialFinish: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val insets = WindowInsets.systemBars.asPaddingValues()
    val density = LocalDensity.current
    var currentIndex by remember { mutableStateOf(0) }

    // 🔁 Animation that runs continuously around the border
    val infiniteTransition = rememberInfiniteTransition(label = "snake")
    val animProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "snakeAnim"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        content()

        if (isTutorialEnabled && listComponents.isNotEmpty() && currentIndex < listComponents.size) {
            val currentRect = listComponents[currentIndex]

            if (currentRect != null) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                        .pointerInput(currentIndex) {
                            detectTapGestures { offset ->
                                val topInset = with(density) { insets.calculateTopPadding().toPx() }
                                val leftInset = with(density) { insets.calculateLeftPadding(LayoutDirection.Ltr).toPx() }

                                val rect = Rect(
                                    left = currentRect.left - leftInset,
                                    top = currentRect.top - topInset,
                                    right = currentRect.right - leftInset,
                                    bottom = currentRect.bottom - topInset
                                )

                                if (rect.contains(offset)) {
                                    if (currentIndex < listComponents.lastIndex) currentIndex++
                                    else onTutorialFinish()
                                }
                            }
                        }
                ) {
                    val topInset = with(density) { insets.calculateTopPadding().toPx() }
                    val leftInset = with(density) { insets.calculateLeftPadding(LayoutDirection.Ltr).toPx() }

                    val spotlightOffset = Offset(
                        currentRect.left - leftInset,
                        currentRect.top - topInset
                    )

                    // 1️⃣ Draw dim background
                    drawRect(Color.Black.copy(alpha = 0.6f), size = size)

                    // 2️⃣ Cut transparent hole
                    drawRoundRect(
                        color = Color.Transparent,
                        topLeft = spotlightOffset,
                        size = currentRect.size,
                        cornerRadius = CornerRadius(16.dp.toPx()),
                        blendMode = BlendMode.Clear
                    )

                    // 3️⃣ Draw “snake” animated border
                    val borderRect = Rect(
                        offset = spotlightOffset,
                        size = currentRect.size
                    )

                    val composePath = Path().apply {
                        addRoundRect(
                            RoundRect(
                                borderRect,
                                CornerRadius(16.dp.toPx(), 16.dp.toPx())
                            )
                        )
                    }

                    val pathMeasure = android.graphics.PathMeasure(composePath.asAndroidPath(), true)
                    val length = pathMeasure.length
                    val segment = Path()
                    val segmentLength = length * 0.25f // snake length (25% of perimeter)
                    val start = length * animProgress
                    val end = (start + segmentLength) % length

                    if (end > start) {
                        pathMeasure.getSegment(start, end, segment.asAndroidPath(), true)
                    } else {
                        // Wrap around end of path
                        pathMeasure.getSegment(start, length, segment.asAndroidPath(), true)
                        pathMeasure.getSegment(0f, end, segment.asAndroidPath(), true)
                    }

                    drawPath(
                        path = segment,
                        color = Color.Cyan,
                        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
            }
        }
    }
}


