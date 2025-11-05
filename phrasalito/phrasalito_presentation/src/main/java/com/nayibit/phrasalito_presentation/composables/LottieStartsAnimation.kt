package com.nayibit.phrasalito_presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay


@Composable
fun LottieStartsAnimation(modifier: Modifier = Modifier) {

    var showAnimation by rememberSaveable { mutableStateOf(true) }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("stars.json")
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1 // play once
    )


    if (showAnimation)
     Box(modifier.fillMaxSize().background(Color.Transparent), contentAlignment = Alignment.Center){
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = modifier.size(200.dp)
        )
         LaunchedEffect(Unit) {
             delay(1500)
             showAnimation = false
         }
    }

}