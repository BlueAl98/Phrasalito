package com.nayibit.feature_categories.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nayibit.feature_categories.presentation.model.CategoryUi
import com.nayibit.feature_categories.presentation.model.LearningColors
import kotlinx.coroutines.delay


@Composable
fun AnimatedCategoryCard(
    category: CategoryUi,
    colors: LearningColors,
    index: Int
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(index * 90L) // stagger effect
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(500)
        ) + slideInVertically(
            animationSpec = tween(500),
            initialOffsetY = { it / 2 }
        ) + scaleIn(
            initialScale = 0.9f,
            animationSpec = tween(500)
        )
    ) {
        CategoryCard(category, colors)
    }
}


@Composable
fun CategoryCard(
    topic: CategoryUi,
    colors: LearningColors
){
    val percent = (topic.progress * 100).toInt()

    Card(
        colors = CardDefaults.cardColors(
            containerColor = colors.card
        ),
        border = BorderStroke(1.dp, colors.cardBorder),
        shape = RoundedCornerShape(20.dp)
    ) {

        Box(
            modifier = Modifier.padding(18.dp)
        ) {

            Column {

                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(colors.cardBorder),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = topic.icon,
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = topic.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colors.textPrimary
                )

                Text(
                    text = topic.subtitle,
                    fontSize = 14.sp,
                    color = colors.primary
                )

                Spacer(Modifier.height(14.dp))

                LinearProgressIndicator(
                    progress = topic.progress,
                    color = colors.primary,
                    trackColor = colors.progressTrack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(50))
                )
            }

            ProgressBadge(percent, colors, Modifier.align(Alignment.TopEnd))
        }
    }
}

@Composable
fun ProgressBadge(
    percent: Int,
    colors: LearningColors,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(colors.badge)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = "$percent%",
            color = colors.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}