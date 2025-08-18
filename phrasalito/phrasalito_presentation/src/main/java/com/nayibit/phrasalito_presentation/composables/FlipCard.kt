package com.nayibit.phrasalito_presentation.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun FlipCard(
    modifier: Modifier = Modifier,
    phrase: String,
    translation: String,
    frontGradient: List<Color> = listOf(Color(0xFF56CCF2), Color(0xFF2F80ED)),
    backGradient: List<Color> =listOf(Color(0xFFF2994A), Color(0xFFF2C94C)),
    shape: RoundedCornerShape = RoundedCornerShape(12.dp)
) {
    var flipped by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "rotationY"
    )

    Box(
        modifier = Modifier
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8 * density // to give a 3D feel
            }
            .clickable(
                onClick = { flipped = !flipped },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(16.dp)
    ) {
        if (rotation <= 90f) {
            FaceCard(
                text = phrase,
                gradient = frontGradient,
                shape = shape
            )
        } else {
            Box(
                modifier = Modifier
                    .graphicsLayer { rotationY = 180f }
            ) {
                FaceCard(
                    text = translation,
                    gradient = backGradient,
                    shape = shape
                )
            }
        }
    }
}



@Composable
private fun FaceCard(
    text: String,
    gradient: List<Color>,
    shape: RoundedCornerShape,
) {
    Box(
        modifier = Modifier
            .background(Brush.linearGradient(gradient), shape)
            .fillMaxWidth()
            .padding(18.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 6
        )
    }
}

// --- Preview / usage example ---
@Composable
@Preview
fun FlashcardPreview() {
    MaterialTheme {
        Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            FlipCard(
                phrase = "Hello, my name is najib bukele and my age is 27",
                translation = "Hola, mi nombre es najib bukele y mi edad es 27",
                frontGradient = listOf(Color(0xFF56CCF2), Color(0xFF2F80ED)),
                backGradient = listOf(Color(0xFFF2994A), Color(0xFFF2C94C))
            )
        }
    }
}