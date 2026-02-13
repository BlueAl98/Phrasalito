package com.nayibit.utils.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nayibit.utils.ui.theme.primaryGradientStart

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    indicatorColor: Color = primaryGradientStart,
    strokeWidth: Dp = 4.dp
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .wrapContentSize(Alignment.Center)
            .testTag("loading_indicator")
    ) {
        LoadingIndicator(
            modifier = modifier.size(70.dp),
            color = indicatorColor
        )

    }
}