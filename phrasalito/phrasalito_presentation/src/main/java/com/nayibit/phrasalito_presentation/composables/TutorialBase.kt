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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.nayibit.phrasalito_presentation.model.TutorialStep
import com.nayibit.phrasalito_presentation.utils.LabelPosition


@Composable
fun TutorialBase(
    listComponents: List<TutorialStep>,
    currentIndex: Int = 0,
    isTutorialEnabled: Boolean = true,
    onTutorialFinish: () -> Unit = {},
    onNextStep: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val insets = WindowInsets.systemBars.asPaddingValues()
    val density = LocalDensity.current
  //  var currentIndex by remember { mutableIntStateOf(currentIndex) }
    val configuration = LocalConfiguration.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }
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


            //  if (currentRect.rect != null) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                    .pointerInput(currentIndex) {
                        detectTapGestures { offset ->
                            val topInset = with(density) { insets.calculateTopPadding().toPx() }
                            val leftInset = with(density) {
                                insets.calculateLeftPadding(LayoutDirection.Ltr).toPx()
                            }

                            val rect = Rect(
                                left = currentRect.rect.left - leftInset,
                                top = currentRect.rect.top - topInset,
                                right = currentRect.rect.right - leftInset,
                                bottom = currentRect.rect.bottom - topInset
                            )

                            if (rect.contains(offset)) {
                                if (currentIndex < listComponents.lastIndex) onNextStep()
                                else onTutorialFinish()
                            }
                        }
                    }
            ) {
                val topInset = with(density) { insets.calculateTopPadding().toPx() }
                val leftInset =
                    with(density) { insets.calculateLeftPadding(LayoutDirection.Ltr).toPx() }

                val spotlightOffset = Offset(
                    currentRect.rect.left - leftInset,
                    currentRect.rect.top - topInset
                )

                // 1️⃣ Draw dim background
                drawRect(Color.Black.copy(alpha = 0.6f), size = size)

                // 2️⃣ Cut transparent hole
                drawRoundRect(
                    color = Color.Transparent,
                    topLeft = spotlightOffset,
                    size = currentRect.rect.size,
                    cornerRadius = CornerRadius(16.dp.toPx()),
                    blendMode = BlendMode.Clear
                )

                // 3️⃣ Draw “snake” animated border
                val borderRect = Rect(
                    offset = spotlightOffset,
                    size = currentRect.rect.size
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



            val modifier = with(density) {
                val offsetModifier = when (currentRect.labelPosition) {
                    LabelPosition.Top -> Modifier.offset(
                        x = currentRect.rect.left.toDp(),
                        y = (currentRect.rect.top.toDp() - 60.dp)
                    )
                    LabelPosition.Bottom -> Modifier.offset(
                        x = currentRect.rect.left.toDp() + 5.dp,
                        y = (currentRect.rect.bottom.toDp())
                    )
                    LabelPosition.Left -> Modifier.offset(
                        // label on left side of rect
                        x = 16.dp, // small padding from left edge
                        y = currentRect.rect.top.toDp() - insets.calculateBottomPadding()
                    )
                    LabelPosition.Right -> Modifier.offset(
                        // label on right side of rect
                        x = (currentRect.rect.right.toDp() + 16.dp),
                        y = currentRect.rect.top.toDp()
                    )
                }

                offsetModifier
            }

            val maxWidth = with(density) {
                when (currentRect.labelPosition) {
                    LabelPosition.Left -> currentRect.rect.left.toDp() - 16.dp
                    LabelPosition.Right -> (screenWidthPx - currentRect.rect.right).toDp() - 16.dp
                    else -> configuration.screenWidthDp.dp - 32.dp // full width minus padding
                }
            }

            Box(
                modifier = modifier
                    .widthIn(max = maxWidth)
            ){
                Text(currentRect.description)
            }


        }
        //  }
    }
}




