package com.nayibit.phrasalito_presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DotsIndicator(
    totalDots: Int,
    currentDot: Int,
    colors: OnboardingColors
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(if (index == currentDot) 24.dp else 8.dp)
                    .background(
                        color = if (index == currentDot) {
                            colors.primaryGradientStart
                        } else {
                            colors.dotInactive
                        },
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        }
    }
}