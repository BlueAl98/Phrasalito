package com.nayibit.phrasalito_presentation.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.roundToInt



enum class SwipeDirection {
    StartToEnd, // izquierda ➝ derecha
    EndToStart  // derecha ➝ izquierda
}

@Composable
fun SwipeableItemWithActions(
    isRevealed: Boolean,
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    direction: SwipeDirection = SwipeDirection.StartToEnd, // 👈 nuevo parámetro
    onExpanded: () -> Unit = {},
    onCollapsed: () -> Unit = {},
    content: @Composable () -> Unit
) {
    var contextMenuWidth by remember { mutableFloatStateOf(0f) }
    val offset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    // 👇 calcula target segun direction
    LaunchedEffect(isRevealed, contextMenuWidth, direction) {
        if (isRevealed) {
            val target = if (direction == SwipeDirection.StartToEnd) contextMenuWidth else -contextMenuWidth
            offset.animateTo(target)
        } else {
            offset.animateTo(0f)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier
                .onSizeChanged { contextMenuWidth = it.width.toFloat() }
                .then(
                    if (direction == SwipeDirection.StartToEnd)
                        Modifier.align(Alignment.CenterStart)
                    else
                        Modifier.align(Alignment.CenterEnd)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            actions()
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offset.value.roundToInt(), 0) }
                .pointerInput(contextMenuWidth, direction) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = (offset.value + dragAmount).coerceIn(
                                    if (direction == SwipeDirection.StartToEnd)
                                        0f..contextMenuWidth
                                    else
                                        -contextMenuWidth..0f
                                )
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                val threshold = contextMenuWidth / 2f
                                val expanded = if (direction == SwipeDirection.StartToEnd) {
                                    offset.value >= threshold
                                } else {
                                    offset.value <= -threshold
                                }

                                if (expanded) {
                                    offset.animateTo(
                                        if (direction == SwipeDirection.StartToEnd) contextMenuWidth else -contextMenuWidth
                                    )
                                    onExpanded()
                                } else {
                                    offset.animateTo(0f)
                                    onCollapsed()
                                }
                            }
                        }
                    )
                }
        ) {
            content()
        }
    }
}