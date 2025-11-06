package com.nayibit.phrasalito_presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun TutorialBase(
    listComponents: List<Rect?>,
    isTutorialEnabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val insets = WindowInsets.systemBars.asPaddingValues()
    val density = LocalDensity.current

    Box(modifier = Modifier.fillMaxSize()) {
        // Normal UI content
        content()

        // Draw overlay only if tutorial is active
        if (isTutorialEnabled && listComponents.isNotEmpty()) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                  //  .pointerInput(Unit) {} // optional: block touches behind
            ) {
                val topInset = with(density) { insets.calculateTopPadding().toPx() }
                val leftInset = with(density) { insets.calculateLeftPadding(LayoutDirection.Ltr).toPx() }

                listComponents.filterNotNull().forEach { rect ->
                    drawRoundRect(
                        color = Color.Red,
                        topLeft = Offset(
                            rect.left - leftInset,
                            rect.top - topInset
                        ),
                        size = rect.size,
                        style = Stroke(width = 3.dp.toPx())
                    )
                }
            }
        }
    }
}
